package za.co.technoris.swingy.Database;

import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import za.co.technoris.swingy.Helpers.CharacterTypes;
import za.co.technoris.swingy.Helpers.LoggerHelper;
import za.co.technoris.swingy.Controllers.CharacterFactory;
import za.co.technoris.swingy.Models.Artifacts.Armor;
import za.co.technoris.swingy.Models.Artifacts.Artifact;
import za.co.technoris.swingy.Models.Artifacts.Helm;
import za.co.technoris.swingy.Models.Artifacts.Weapon;
import za.co.technoris.swingy.Models.Characters.*;
import za.co.technoris.swingy.Models.Characters.Character;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static za.co.technoris.swingy.Helpers.GlobalHelper.*;


/**
 * Created by mmbatha on 4/21/17.
 */
public class DatabaseHandler {

    private static final String DATABASE_NAME = "swingy.db";
    private static final String TABLE_HEROES = "heroes";
    private static final String TBL_KEY_ID = "id";
    private static final String TBL_KEY_NAME = "name";
    private static final String TBL_KEY_TYPE = "type";
    private static final String TBL_KEY_LEVEL = "level";
    private static final String TBL_KEY_XP = "xp";
    private static final String TBL_KEY_ATTACK = "attack";
    private static final String TBL_KEY_DEFENSE = "defense";
    private static final String TBL_KEY_HP = "hp";
    private static final String TBL_KEY_WEAPON = "weapon";
    private static final String TBL_KEY_ARMOR = "armor";
    private static final String TBL_KEY_HELM = "helm";
    private static final String STATEMENT_CREATE_HEROES_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_HEROES + " (" + TBL_KEY_ID + " INTEGER PRIMARY KEY, " +
                    TBL_KEY_NAME + " TEXT, " + TBL_KEY_TYPE + " TEXT, " + TBL_KEY_LEVEL + " INTEGER, " +
                    TBL_KEY_XP + " INTEGER, " + TBL_KEY_ATTACK + " INTEGER, " + TBL_KEY_DEFENSE + " INTEGER, " +
                    TBL_KEY_HP + " INTEGER, " + TBL_KEY_WEAPON + " BLOB, " + TBL_KEY_ARMOR + " BLOB, " +
                    TBL_KEY_HELM + " BLOB)";
    private static final String STATEMENT_INSERT_HERO =
            "INSERT INTO " + TABLE_HEROES + " (" +  TBL_KEY_NAME + "," + TBL_KEY_TYPE + "," +
                    TBL_KEY_LEVEL + "," + TBL_KEY_XP + "," + TBL_KEY_ATTACK + "," + TBL_KEY_DEFENSE + "," +
                    TBL_KEY_HP + "," + TBL_KEY_WEAPON + "," + TBL_KEY_ARMOR + "," + TBL_KEY_HELM +
                    ") VALUES(?,?,?,?,?,?,?,?,?,?)";
    private static final String STATEMENT_GET_HEROES_TABLE = "SELECT * FROM " + TABLE_HEROES;
    private static final String STATEMENT_GET_HERO_DATA_BY_NAME = "SELECT * FROM " + TABLE_HEROES + " WHERE " + TBL_KEY_NAME + " = ?";
    private static final String STATEMENT_UPDATE_HERO_DATA = "UPDATE " + TABLE_HEROES + " SET " +
            TBL_KEY_LEVEL + " = ?, " + TBL_KEY_XP + " = ?, " + TBL_KEY_ATTACK + " = ?," + TBL_KEY_DEFENSE + " = ?," +
            TBL_KEY_HP + " = ?, " + TBL_KEY_WEAPON + " = ?," + TBL_KEY_ARMOR + " = ?," + TBL_KEY_HELM + " = ? WHERE " +
            TBL_KEY_NAME + " = ?";
    private static final String STATEMENT_DELETE_HERO_DATA_BY_NAME = "DELETE from " + TABLE_HEROES + " WHERE " + TBL_KEY_NAME + " = ?";

    private static final String SQLITE_DRIVER = "org.sqlite.JDBC";
    private static final String SQLITE_URL = "jdbc:sqlite:" + DATABASE_NAME;

    private Connection dbConnection;
    private Statement statement;
    private static DatabaseHandler databaseHandler;

    private byte[] byteArray;
    private ByteArrayInputStream inputStream;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public static synchronized DatabaseHandler getInstance() {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler();
        }
        return (databaseHandler);
    }

    private Connection connectToDB() {
        try {
            Class.forName(SQLITE_DRIVER);
            dbConnection = DriverManager.getConnection(SQLITE_URL);
            statement = dbConnection.createStatement();
            statement.executeUpdate(STATEMENT_CREATE_HEROES_TABLE);
        } catch (SQLException | ClassNotFoundException e) {
            LoggerHelper.print("SQLException - connectToDB(): " + e.getMessage());
            System.exit(0);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return (dbConnection);
    }

    private ByteArrayInputStream serializeObject(Hero hero, Artifact a) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);
        switch (a) {
            case WEAPON:
                objOutputStream.writeObject(hero.getWeapon());
                break ;
            case ARMOR:
                objOutputStream.writeObject(hero.getArmor());
                break ;
            case HELM:
                objOutputStream.writeObject(hero.getHelm());
                break ;
            default:
                return (null);
        }
        byteArray = outputStream.toByteArray();
        inputStream = new ByteArrayInputStream(byteArray);
        return (inputStream);
    }

    private Object deserializeObject(ResultSet resultSet, String key) throws SQLException, IOException, ClassNotFoundException {
        byteArray = resultSet.getBytes(key);
        inputStream = new ByteArrayInputStream(byteArray);
        ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
        return (objInputStream.readObject());
    }

    private boolean isDuplicateHero(Connection dbConnection, Hero hero) throws SQLException {
        statement = dbConnection.createStatement();
        resultSet = statement.executeQuery(STATEMENT_GET_HEROES_TABLE);
        while (resultSet.next()) {
            if (hero.getName().equals(resultSet.getString(TBL_KEY_NAME))) {
                return (true);
            }
        }
        return (false);
    }

    public void insertHero(Hero hero) {
        try {
            dbConnection = this.connectToDB();
            if (isDuplicateHero(dbConnection, hero)) {
                LoggerHelper.print("Db: This Hero already exists");
            } else {
                preparedStatement = dbConnection.prepareStatement(STATEMENT_INSERT_HERO);
                preparedStatement.setString(1, hero.getName());
                preparedStatement.setString(2, hero.getType());
                preparedStatement.setInt(3, hero.getLevel());
                preparedStatement.setInt(4, hero.getXp());
                preparedStatement.setInt(5, hero.getAttack());
                preparedStatement.setInt(6, hero.getDefense());
                preparedStatement.setInt(7, hero.getHp());
                preparedStatement.setBinaryStream(8, serializeObject(hero, Artifacts.WEAPON), byteArray.length);
                preparedStatement.setBinaryStream(9, serializeObject(hero, Artifacts.ARMOR), byteArray.length);
                preparedStatement.setBinaryStream(10, serializeObject(hero, Artifacts.HELM), byteArray.length);
                preparedStatement.executeUpdate();
                LoggerHelper.print("Db: <" + hero.getName() + ">" + " created");
            }
        } catch (SQLException | IOException e) {
            LoggerHelper.print("SQLException - connectToDB(): " + e.getMessage());
            System.exit(0);
        } finally {
            closeAll();
        }
    }

    public List<Hero> getDB() {
        try {
            List<Hero> heroList = new ArrayList<>();

            dbConnection = this.connectToDB();
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery(STATEMENT_GET_HEROES_TABLE);
            while (resultSet.next()) {
                isHero = true;
                Hero hero = null;
                switch (resultSet.getString(TBL_KEY_TYPE)) {
                    case "Warrior":
                        hero = new Warrior();
                        break;
                    case "Thief":
                        hero = new Thief();
                        break;
                    case "Wizard":
                        hero = new Wizard();
                }
                assert hero != null;
                hero.setName(resultSet.getString(TBL_KEY_NAME));
                hero.setType(resultSet.getString(TBL_KEY_TYPE));
                hero.setLevel(resultSet.getInt(TBL_KEY_LEVEL));
                hero.setXp(resultSet.getInt(TBL_KEY_XP));
                hero.setAttack(resultSet.getInt(TBL_KEY_ATTACK));
                hero.setDefense(resultSet.getInt(TBL_KEY_DEFENSE));
                hero.setHp(resultSet.getInt(TBL_KEY_HP));
                hero.setWeapon((Weapon) deserializeObject(resultSet, TBL_KEY_WEAPON));
                hero.setArmor((Armor) deserializeObject(resultSet, TBL_KEY_ARMOR));
                hero.setHelm((Helm) deserializeObject(resultSet, TBL_KEY_HELM));
                heroList.add(hero);
            }
            return (heroList);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            LoggerHelper.print("SQLException - connectToDB(): " + e.getMessage());
            System.exit(0);
        } finally {
            closeAll();
        }
        return (null);
    }

    public void printDB() {
        try {
            StringBuilder sb = new StringBuilder();
            dbConnection = this.connectToDB();
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery(STATEMENT_GET_HEROES_TABLE);
            if (isGUI) {
                jtaLog.setText("");
            }
            heroNumber = 0;
            while (resultSet.next()) {
                isHero = true;
                sb.append("Name: ").append(resultSet.getString(TBL_KEY_NAME)).append("\n")
                        .append("Type: ").append(resultSet.getString(TBL_KEY_TYPE)).append("\n")
                        .append("Level: ").append(resultSet.getInt(TBL_KEY_LEVEL)).append("\n")
                        .append("Experience: ").append(resultSet.getInt(TBL_KEY_XP)).append("\n")
                        .append("Attack: ").append(resultSet.getInt(TBL_KEY_ATTACK)).append("\n")
                        .append("Defense: ").append(resultSet.getInt(TBL_KEY_DEFENSE)).append("\n")
                        .append("Health: ").append(resultSet.getInt(TBL_KEY_HP)).append("\n")
                        .append("Weapon: ").append(((Artifacts) deserializeObject(resultSet, TBL_KEY_WEAPON)).getName()).append("\n")
                        .append("Armor: ").append(((Artifacts) deserializeObject(resultSet, TBL_KEY_ARMOR)).getName()).append("\n")
                        .append("Helm: ").append(((Artifacts) deserializeObject(resultSet, TBL_KEY_HELM)).getName()).append("\n\n");
                heroNumber += 1;
            }
            LoggerHelper.print(sb.toString());
        } catch (SQLException | ClassNotFoundException | IOException e) {
            LoggerHelper.print("SQLException - connectToDB(): " + e.getMessage());
            System.exit(0);
        } finally {
            closeAll();
        }
    }

    public boolean isValidId(int id) {
        try {
            dbConnection = this.connectToDB();
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery(STATEMENT_GET_HEROES_TABLE);

            while (resultSet.next()) {
                if (resultSet.getInt(TBL_KEY_ID) == id) {
                    return (true);
                }
            }
        } catch (SQLException e) {
            LoggerHelper.print("SQLException - connectToDB(): " + e.getMessage());
            System.exit(0);
        } finally {
            closeAll();
        }
        return (false);
    }

    public void updateHero(Character hero) {
        try {
            dbConnection = this.connectToDB();
            preparedStatement = dbConnection.prepareStatement(STATEMENT_UPDATE_HERO_DATA);
            preparedStatement.setInt(1, hero.getLevel());
            preparedStatement.setInt(2, ((Hero)hero).getXp());
            preparedStatement.setInt(3, hero.getAttack());
            preparedStatement.setInt(4, hero.getDefense());
            preparedStatement.setInt(5, hero.getHp());
            preparedStatement.setBinaryStream(6, serializeObject(((Hero)hero), Artifacts.WEAPON), byteArray.length);
            preparedStatement.setBinaryStream(7, serializeObject(((Hero)hero), Artifacts.ARMOR), byteArray.length);
            preparedStatement.setBinaryStream(8, serializeObject(((Hero)hero), Artifacts.HELM), byteArray.length);
            preparedStatement.setString(9, hero.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException e) {
            LoggerHelper.print("SQLException - connectToDB(): " + e.getMessage());
            System.exit(0);
        } finally {
            closeAll();
        }
    }

    public void deleteHero(String name) {
        try {
            dbConnection = this.connectToDB();
            preparedStatement = dbConnection.prepareStatement(STATEMENT_DELETE_HERO_DATA_BY_NAME);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LoggerHelper.print("SQLException - connectToDB(): " + e.getMessage());
            System.exit(0);
        } finally {
            closeAll();
        }
    }

    public Hero getHeroData(String name) {
        Hero hero = null;

        try {
            dbConnection = this.connectToDB();
            preparedStatement = dbConnection.prepareStatement(STATEMENT_GET_HERO_DATA_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                switch (resultSet.getString(TBL_KEY_TYPE)) {
                    case "Warrior":
                        hero = (Hero) CharacterFactory.newHero(name, CharacterTypes.WARRIOR);
                        break;
                    case "Thief":
                        hero = (Hero) CharacterFactory.newHero(name, CharacterTypes.THIEF);
                        break;
                    case "Wizard":
                        hero = (Hero) CharacterFactory.newHero(name, CharacterTypes.WIZARD);
                        break;
                }
                assert hero != null;
                hero.setLevel(resultSet.getInt(TBL_KEY_LEVEL));
                hero.setXp(resultSet.getInt(TBL_KEY_XP));
                hero.setAttack(resultSet.getInt(TBL_KEY_ATTACK));
                hero.setDefense(resultSet.getInt(TBL_KEY_DEFENSE));
                hero.setHp(resultSet.getInt(TBL_KEY_HP));
                hero.setWeapon((Weapon) deserializeObject(resultSet, TBL_KEY_WEAPON));
                hero.setArmor((Armor) deserializeObject(resultSet, TBL_KEY_ARMOR));
                hero.setHelm((Helm) deserializeObject(resultSet, TBL_KEY_HELM));
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            LoggerHelper.print("SQLException - connectToDB(): " + e.getMessage());
            System.exit(0);
        } finally {
            closeAll();
        }
        return (hero);
    }

    private void closeAll() {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            if (dbConnection != null && !dbConnection.isClosed()) {
                dbConnection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
