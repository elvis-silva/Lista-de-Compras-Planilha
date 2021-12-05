package com.elvis.shopping.list.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elvis.shopping.list.model.ConfigData;
import com.elvis.shopping.list.model.ListModel;
import com.elvis.shopping.list.model.ListName;
import com.elvis.shopping.list.model.PantryRow;
import com.elvis.shopping.list.model.Product;

public class DataHandler {

    public static final String TEMP_SHOPPING_LIST_TABLE     = "TempShoppingListTable";
    public static final String SHOPPING_LIST_TABLE          = "ShoppingListTable";
    public static final String PRODUCT_ID                   = "product_id";
    public static final String PRODUCT_NAME                 = "product_name";
    public static final String PRODUCT_QUANT                = "product_quant";
    public static final String PRODUCT_PRICE                = "product_price";
    public static final String SCRATCHED                    = "scratched";
    public static final String PRODUCT_CATEGORY             = "product_category";
    public static final String PRODUCT_UNIT                 = "product_unit";
//    public static final String PRODUCT_DESCRIPTION          = "product_description";
    public static final String [] PRODUCT_COLUMNS           = {PRODUCT_ID, PRODUCT_NAME, PRODUCT_QUANT, PRODUCT_PRICE, SCRATCHED, PRODUCT_CATEGORY, PRODUCT_UNIT};//, PRODUCT_DESCRIPTION};

    public static final String TEMP_CONFIG_LIST_TABLE               = "TempConfigListTable";
    public static final String CONFIG_LIST_TABLE                    = "ConfigListTable";
    public static final String CONFIG_LIST_ID                       = "config_list_id";
    public static final String CURRENT_BG_COLOR                     = "current_bg_color";
    public static final String CURRENT_FONT_COLOR                   = "current_font_color";
    public static final String CURRENT_FONT_INSERT_COLOR            = "current_font_insert_color";
    public static final String CURRENT_TITLES_FONT_COLOR            = "current_titles_font_color";
    public static final String CURRENT_MESSAGE_FONT_COLOR           = "current_message_font_color";
    public static final String CURRENT_PRODUCT_ADDED_FONT_COLOR     = "current_product_added_font_color";
    public static final String CURRENT_PRODUCT_CANCELED_FONT_COLOR  = "current_product_canceled_font_color";
    public static final String CURRENT_PRODUCT_SCRATCHED_FONT_COLOR = "current_product_scratched_font_color";
    public static final String CURRENT_CATEGORY_TITLE_FONT_COLOR    = "current_category_title_font_color";
    public static final String CURRENT_CATEGORY_TITLE_BG_COLOR      = "current_category_title_bg_color";
    public static final String [] CONFIG_LIST_COLUMNS               = {CONFIG_LIST_ID, CURRENT_BG_COLOR, CURRENT_FONT_COLOR,
            CURRENT_FONT_INSERT_COLOR, CURRENT_TITLES_FONT_COLOR, CURRENT_MESSAGE_FONT_COLOR, CURRENT_PRODUCT_ADDED_FONT_COLOR,
            CURRENT_PRODUCT_CANCELED_FONT_COLOR, CURRENT_PRODUCT_SCRATCHED_FONT_COLOR, CURRENT_CATEGORY_TITLE_FONT_COLOR, CURRENT_CATEGORY_TITLE_BG_COLOR};

    public static final String TEMP_SAVE_LIST_TABLE         = "TempSaveListTable";
    public static final String SAVE_LIST_TABLE              = "SaveListTable";
    public static final String LIST_ID                      = "list_id";
    public static final String LIST_NAME                    = "list_name";
    public static final String LIST_PRODUCT_NAME            = "list_product_name";
    public static final String LIST_PRODUCT_QUANT           = "list_product_quant";
    public static final String LIST_PRODUCT_PRICE           = "list_product_price";
    public static final String LIST_PRODUCT_SCRATCHED       = "list_product_scratched";
    public static final String LIST_PRODUCT_CATEGORY        = "list_product_category";
    public static final String LIST_PRODUCT_UNIT            = "list_product_unit";
    public static final String [] SAVE_LIST_COLUMNS         = {LIST_ID, LIST_NAME, LIST_PRODUCT_NAME, LIST_PRODUCT_QUANT,
            LIST_PRODUCT_PRICE, LIST_PRODUCT_SCRATCHED, LIST_PRODUCT_CATEGORY, LIST_PRODUCT_UNIT};

    public static final String TEMP_LISTS_NAMES_TABLE       = "TempListsNamesTable";
    public static final String LISTS_NAMES_TABLE            = "ListsNamesTable";
    public static final String LISTS_NAMES_ID               = "lists_names_id";
    public static final String LISTS_NAMES_NAME             = "lists_names_name";
    public static final String [] LISTS_NAMES_COLUMNS       = {LISTS_NAMES_ID, LISTS_NAMES_NAME};

    public static final String TEMP_LIST_LOADED_TABLE       = "TempListLoadedTable";
    public static final String LIST_LOADED_TABLE            = "ListLoadedTable";
    public static final String LIST_LOADED_ID               = "list_loaded_id";
    public static final String LIST_LOADED_NAME             = "list_loaded_name";
    public static final String [] LIST_LOADED_COLUMNS       = {LIST_LOADED_ID, LIST_LOADED_NAME};

    public static final String TEMP_PANTRY_TABLE            = "TempPantryTable";
    public static final String PANTRY_TABLE                 = "PantryTable";
    public static final String PANTRY_ID                    = "pantry_id";
    public static final String PANTRY_PRODUCT_NAME          = "pantry_product_name";
    public static final String PANTRY_PRODUCT_QUANT         = "pantry_product_quant";
    public static final String PANTRY_PRODUCT_UNIT          = "pantry_product_unit";
    public static final String PANTRY_PRODUCT_CATEGORY      = "pantry_product_category";
    public static final String [] PANTRY_COLUMNS            = {PANTRY_ID, PANTRY_PRODUCT_NAME, PANTRY_PRODUCT_QUANT,
                                            PANTRY_PRODUCT_UNIT, PANTRY_PRODUCT_CATEGORY};

    public static final String DATABASE_NAME = "elvislistadecompras.db";
    public static final int DATABASE_VERSION = 6;

    public static final String DATABASE_CREATE              = "CREATE TABLE " +
                               SHOPPING_LIST_TABLE          + "(" +
                               PRODUCT_ID                   + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                               PRODUCT_NAME                 + " TEXT," +
                               PRODUCT_QUANT                + " TEXT," +
                               PRODUCT_PRICE                + " TEXT," +
                               SCRATCHED                    + " TEXT," +
                               PRODUCT_CATEGORY             + " TEXT," +
                               PRODUCT_UNIT                 + " TEXT);";

    public static final String DATABASE_CONFIG_LIST_CREATE              = "CREATE TABLE " +
                               CONFIG_LIST_TABLE                        + "(" +
                               CONFIG_LIST_ID                           + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                               CURRENT_BG_COLOR                         + " INT," +
                               CURRENT_FONT_COLOR                       + " INT," +
                               CURRENT_FONT_INSERT_COLOR                + " INT," +
                               CURRENT_TITLES_FONT_COLOR                + " INT," +
                               CURRENT_MESSAGE_FONT_COLOR               + " INT," +
                               CURRENT_PRODUCT_ADDED_FONT_COLOR         + " INT," +
                               CURRENT_PRODUCT_CANCELED_FONT_COLOR      + " INT," +
                               CURRENT_PRODUCT_SCRATCHED_FONT_COLOR     + " INT," +
                               CURRENT_CATEGORY_TITLE_FONT_COLOR        + " INT," +
                               CURRENT_CATEGORY_TITLE_BG_COLOR          + " INT);";

    public static final String DATABASE_SAVE_LIST_CREATE    = "CREATE TABLE " +
                               SAVE_LIST_TABLE              + "(" +
                               LIST_ID                      + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                               LIST_NAME                    + " TEXT," +
                               LIST_PRODUCT_NAME            + " TEXT," +
                               LIST_PRODUCT_QUANT           + " TEXT," +
                               LIST_PRODUCT_PRICE           + " TEXT," +
                               LIST_PRODUCT_SCRATCHED       + " TEXT," +
                               LIST_PRODUCT_CATEGORY        + " TEXT," +
                               LIST_PRODUCT_UNIT            + " TEXT);";

    public static final String DATABASE_LISTS_NAMES         = "CREATE TABLE " +
                               LISTS_NAMES_TABLE            + "(" +
                               LISTS_NAMES_ID               + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                               LISTS_NAMES_NAME             + " TEXT);";

    public static final String DATABASE_LIST_LOADED         = "CREATE TABLE " +
                               LIST_LOADED_TABLE            + "(" +
                               LIST_LOADED_ID               + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                               LIST_LOADED_NAME             + " TEXT);";

    public static final String DATABASE_PANTRY              = "CREATE TABLE " +
                               PANTRY_TABLE                 + "(" +
                               PANTRY_ID                    + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                               PANTRY_PRODUCT_NAME          + " TEXT," +
                               PANTRY_PRODUCT_QUANT         + " TEXT," +
                               PANTRY_PRODUCT_UNIT          + " TEXT," +
                               PANTRY_PRODUCT_CATEGORY      + " TEXT);";

    DataBaseHelper dbHelper;
    Context context;
    SQLiteDatabase db;

    public DataHandler (Context pContext) {
        context = pContext;
        dbHelper = new DataBaseHelper(pContext);
    }

    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper (Context pContext) {
            super(pContext, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CONFIG_LIST_CREATE);
            db.execSQL(DATABASE_SAVE_LIST_CREATE);
            db.execSQL(DATABASE_LISTS_NAMES);
            db.execSQL(DATABASE_LIST_LOADED);
            db.execSQL(DATABASE_PANTRY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            switch (oldVersion) {
                case 3:
                    updateVersion3(db);
                    break;
                case 4:
                    updateVersion4(db);
                    break;
                case 5:
                    updateVersion5(db);
                    break;
                default:
                    db.execSQL("DROP TABLE IF EXISTS " + SHOPPING_LIST_TABLE);
                    db.execSQL("DROP TABLE IF EXISTS " + CONFIG_LIST_TABLE);
                    db.execSQL("DROP TABLE IF EXISTS " + SAVE_LIST_TABLE);
                    db.execSQL("DROP TABLE IF EXISTS " + LISTS_NAMES_TABLE);
                    db.execSQL("DROP TABLE IF EXISTS " + LIST_LOADED_TABLE);
                    db.execSQL("DROP TABLE IF EXISTS " + PANTRY_TABLE);
                    onCreate(db);
                    break;
            }
        }

        private void updateVersion5(SQLiteDatabase db) {
            /** UPGRADE SHOPPING_LIST_TABLE **/

            // Create an temporaty table that can store data of older version
            String TEMP_CREATE_SHOPPING_LIST_TABLE = "CREATE TABLE " +
                    TEMP_SHOPPING_LIST_TABLE + "(" +
                    PRODUCT_ID          + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    PRODUCT_NAME        + " TEXT," +
                    PRODUCT_QUANT       + " TEXT," +
                    PRODUCT_PRICE       + " TEXT," +
                    SCRATCHED           + " TEXT," +
                    PRODUCT_CATEGORY    + " TEXT);";
            db.execSQL(TEMP_CREATE_SHOPPING_LIST_TABLE);

            // Insert data into temporary table from existing older version ShoppingListTable
            db.execSQL("INSERT INTO " + TEMP_SHOPPING_LIST_TABLE + " SELECT " +
                    PRODUCT_ID          + ", " +
                    PRODUCT_NAME        + ", " +
                    PRODUCT_QUANT       + ", " +
                    PRODUCT_PRICE       + ", " +
                    SCRATCHED           + ", " +
                    PRODUCT_CATEGORY    + " FROM " +
                    SHOPPING_LIST_TABLE);

            // Remove older version database table
            db.execSQL("DROP TABLE " + SHOPPING_LIST_TABLE);

            db.execSQL(DATABASE_CREATE);

            // Insert data from temporary table
            db.execSQL("INSERT INTO " + SHOPPING_LIST_TABLE + " SELECT " +
                    PRODUCT_ID          + ", " +
                    PRODUCT_NAME        + ", " +
                    PRODUCT_QUANT       + ", " +
                    PRODUCT_PRICE       + ", " +
                    SCRATCHED           + ", " +
                    PRODUCT_CATEGORY    + ", " +
                    null                + " FROM " +
                    TEMP_SHOPPING_LIST_TABLE);

            // Remove temporary table
            db.execSQL("DROP TABLE " + TEMP_SHOPPING_LIST_TABLE);

            /** UPGRADE CONFIG_LIST_TABLE **/

            // Create an temporaty table that can store data of older version
            String TEMP_CREATE_CONFIG_LIST_TABLE = "CREATE TABLE " +
                    TEMP_CONFIG_LIST_TABLE                  + "(" +
                    CONFIG_LIST_ID                          + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CURRENT_BG_COLOR                        + " INT," +
                    CURRENT_FONT_COLOR                      + " INT," +
                    CURRENT_FONT_INSERT_COLOR               + " INT," +
                    CURRENT_TITLES_FONT_COLOR               + " INT," +
                    CURRENT_MESSAGE_FONT_COLOR              + " INT," +
                    CURRENT_PRODUCT_ADDED_FONT_COLOR        + " INT," +
                    CURRENT_PRODUCT_CANCELED_FONT_COLOR     + " INT," +
                    CURRENT_PRODUCT_SCRATCHED_FONT_COLOR    + " INT," +
                    CURRENT_CATEGORY_TITLE_FONT_COLOR       + " INT," +
                    CURRENT_CATEGORY_TITLE_BG_COLOR         + " INT);";
            db.execSQL(TEMP_CREATE_CONFIG_LIST_TABLE);

            // Insert data into temporary table from existing older version ConfigListTable
            db.execSQL("INSERT INTO " + TEMP_CONFIG_LIST_TABLE + " SELECT " +
                    CONFIG_LIST_ID                          + ", " +
                    CURRENT_BG_COLOR                        + ", " +
                    CURRENT_FONT_COLOR                      + ", " +
                    CURRENT_FONT_INSERT_COLOR               + ", " +
                    CURRENT_TITLES_FONT_COLOR               + ", " +
                    CURRENT_MESSAGE_FONT_COLOR              + ", " +
                    CURRENT_PRODUCT_ADDED_FONT_COLOR        + ", " +
                    CURRENT_PRODUCT_CANCELED_FONT_COLOR     + ", " +
                    CURRENT_PRODUCT_SCRATCHED_FONT_COLOR    + ", " +
                    CURRENT_CATEGORY_TITLE_FONT_COLOR       + ", " +
                    CURRENT_CATEGORY_TITLE_BG_COLOR         + " FROM " +
                    CONFIG_LIST_TABLE);

            // Remove older version ConfigListTable
            db.execSQL("DROP TABLE " + CONFIG_LIST_TABLE);

            db.execSQL(DATABASE_CONFIG_LIST_CREATE);

            // Insert data from temporary table
            db.execSQL("INSERT INTO " + CONFIG_LIST_TABLE + " SELECT " +
                    CONFIG_LIST_ID                          + ", " +
                    CURRENT_BG_COLOR                        + ", " +
                    CURRENT_FONT_COLOR                      + ", " +
                    CURRENT_FONT_INSERT_COLOR               + ", " +
                    CURRENT_TITLES_FONT_COLOR               + ", " +
                    CURRENT_MESSAGE_FONT_COLOR              + ", " +
                    CURRENT_PRODUCT_ADDED_FONT_COLOR        + ", " +
                    CURRENT_PRODUCT_CANCELED_FONT_COLOR     + ", " +
                    CURRENT_PRODUCT_SCRATCHED_FONT_COLOR    + ", " +
                    CURRENT_CATEGORY_TITLE_FONT_COLOR       + ", " +
                    CURRENT_CATEGORY_TITLE_BG_COLOR         + " FROM " +
                    TEMP_CONFIG_LIST_TABLE);

            // Remove temporary table
            db.execSQL("DROP TABLE " + TEMP_CONFIG_LIST_TABLE);

            /** UPGRADE SAVE_LIST_TABLE **/
            // Create an temporaty table that can store data of older version
            String TEMP_CREATE_SAVE_LIST_TABLE  = "CREATE TABLE " +
                    TEMP_SAVE_LIST_TABLE         + "(" +
                    LIST_ID                      + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    LIST_NAME                    + " TEXT," +
                    LIST_PRODUCT_NAME            + " TEXT," +
                    LIST_PRODUCT_QUANT           + " TEXT," +
                    LIST_PRODUCT_PRICE           + " TEXT," +
                    LIST_PRODUCT_SCRATCHED       + " TEXT," +
                    LIST_PRODUCT_CATEGORY        + " TEXT);";
            db.execSQL(TEMP_CREATE_SAVE_LIST_TABLE);

            // Insert data into temporary table from existing older version SaveListTable
            db.execSQL("INSERT INTO " + TEMP_SAVE_LIST_TABLE + " SELECT " +
                    LIST_ID                      + ", " +
                    LIST_NAME                    + ", " +
                    LIST_PRODUCT_NAME            + ", " +
                    LIST_PRODUCT_QUANT           + ", " +
                    LIST_PRODUCT_PRICE           + ", " +
                    LIST_PRODUCT_SCRATCHED       + ", " +
                    LIST_PRODUCT_CATEGORY        + " FROM " +
                    SAVE_LIST_TABLE);

            // Remove older version SaveListTable
            db.execSQL("DROP TABLE " + SAVE_LIST_TABLE);

            db.execSQL(DATABASE_SAVE_LIST_CREATE);

            // Insert data from temporary table
            db.execSQL("INSERT INTO " + SAVE_LIST_TABLE + " SELECT " +
                    LIST_ID                      + ", " +
                    LIST_NAME                    + ", " +
                    LIST_PRODUCT_NAME            + ", " +
                    LIST_PRODUCT_QUANT           + ", " +
                    LIST_PRODUCT_PRICE           + ", " +
                    LIST_PRODUCT_SCRATCHED       + ", " +
                    LIST_PRODUCT_CATEGORY        + ", " +
                    null                         + " FROM " +
                    TEMP_SAVE_LIST_TABLE);

            // Remove temporary table
            db.execSQL("DROP TABLE " + TEMP_SAVE_LIST_TABLE);

            /** UPGRADE LISTS_NAMES_TABLE **/
            // Create an temporaty table that can store data of older version
            String TEMP_CREATE_LISTS_NAMES_TABLE  = "CREATE TABLE " +
                    TEMP_LISTS_NAMES_TABLE        + "(" +
                    LISTS_NAMES_ID               + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    LISTS_NAMES_NAME             + " TEXT);";
            db.execSQL(TEMP_CREATE_LISTS_NAMES_TABLE);

            // Insert data into temporary table from existing older version ListsNamesTable
            db.execSQL("INSERT INTO " + TEMP_LISTS_NAMES_TABLE + " SELECT " +
                    LISTS_NAMES_ID               + ", " +
                    LISTS_NAMES_NAME             + " FROM " +
                    LISTS_NAMES_TABLE);

            // Remove older version ListsNamesTable
            db.execSQL("DROP TABLE " + LISTS_NAMES_TABLE);

            db.execSQL(DATABASE_LISTS_NAMES);

            // Insert data from temporary table
            db.execSQL("INSERT INTO " + LISTS_NAMES_TABLE + " SELECT " +
                    LISTS_NAMES_ID               + ", " +
                    LISTS_NAMES_NAME             + " FROM " +
                    TEMP_LISTS_NAMES_TABLE);

            // Remove temporary table
            db.execSQL("DROP TABLE " + TEMP_LISTS_NAMES_TABLE);

            /** UPGRADE LIST_LOADED_TABLE **/
            // Create an temporaty table that can store data of older version
            String TEMP_CREATE_LIST_LOADED_TABLE  = "CREATE TABLE " +
                    TEMP_LIST_LOADED_TABLE        + "(" +
                    LIST_LOADED_ID                + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    LIST_LOADED_NAME              + " TEXT);";
            db.execSQL(TEMP_CREATE_LIST_LOADED_TABLE);

            // Insert data into temporary table from existing older version ListsLoadedTable
            db.execSQL("INSERT INTO " + TEMP_LIST_LOADED_TABLE + " SELECT " +
                    LIST_LOADED_ID                + ", " +
                    LIST_LOADED_NAME              + " FROM " +
                    LIST_LOADED_TABLE);

            // Remove older version ListLoadedTable
            db.execSQL("DROP TABLE " + LIST_LOADED_TABLE);

            db.execSQL(DATABASE_LIST_LOADED);

            // Insert data from temporary table
            db.execSQL("INSERT INTO " + LIST_LOADED_TABLE + " SELECT " +
                    LIST_LOADED_ID               + ", " +
                    LIST_LOADED_NAME             + " FROM " +
                    TEMP_LIST_LOADED_TABLE);

            // Remove temporary table
            db.execSQL("DROP TABLE " + TEMP_LIST_LOADED_TABLE);

            /** UPGRADE PANTRY_TABLE **/
            // Create an temporaty table that can store data of older version
            String TEMP_CREATE_DATABASE_PANTRY = "CREATE TABLE " +
                    TEMP_PANTRY_TABLE                 + "(" +
                    PANTRY_ID                    + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    PANTRY_PRODUCT_NAME          + " TEXT," +
                    PANTRY_PRODUCT_QUANT         + " TEXT," +
                    PANTRY_PRODUCT_UNIT          + " TEXT," +
                    PANTRY_PRODUCT_CATEGORY      + " TEXT);";
            db.execSQL(TEMP_CREATE_DATABASE_PANTRY);

            // Insert data into temporary table from existing older version PantryTable
            db.execSQL("INSERT INTO " + TEMP_PANTRY_TABLE + " SELECT " +
                    PANTRY_ID                    + ", " +
                    PANTRY_PRODUCT_NAME          + ", " +
                    PANTRY_PRODUCT_QUANT         + ", " +
                    PANTRY_PRODUCT_UNIT          + ", " +
                    PANTRY_PRODUCT_CATEGORY      + " FROM " +
                    PANTRY_TABLE);

            // Remove older version PantryTable
            db.execSQL("DROP TABLE " + PANTRY_TABLE);

            db.execSQL(DATABASE_PANTRY);

            // Insert data from temporary table
            db.execSQL("INSERT INTO " + PANTRY_TABLE + " SELECT " +
                    PANTRY_ID                    + ", " +
                    PANTRY_PRODUCT_NAME          + ", " +
                    PANTRY_PRODUCT_QUANT         + ", " +
                    PANTRY_PRODUCT_UNIT          + ", " +
                    PANTRY_PRODUCT_CATEGORY      + " FROM " +
                    TEMP_PANTRY_TABLE);

            // Remove temporary table
            db.execSQL("DROP TABLE " + TEMP_PANTRY_TABLE);
        }

        private void updateVersion4(SQLiteDatabase db) {
            /** UPGRADE SHOPPING_LIST_TABLE **/

            // Create an temporaty table that can store data of older version
            String TEMP_CREATE_SHOPPING_LIST_TABLE = "CREATE TABLE " +
                    TEMP_SHOPPING_LIST_TABLE + "(" +
                    PRODUCT_ID          + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    PRODUCT_NAME        + " TEXT," +
                    PRODUCT_QUANT       + " TEXT," +
                    PRODUCT_PRICE       + " TEXT," +
                    SCRATCHED           + " TEXT," +
                    PRODUCT_CATEGORY    + " TEXT);";
            db.execSQL(TEMP_CREATE_SHOPPING_LIST_TABLE);

            // Insert data into temporary table from existing older version ShoppingListTable
            db.execSQL("INSERT INTO " + TEMP_SHOPPING_LIST_TABLE + " SELECT " +
                    PRODUCT_ID          + ", " +
                    PRODUCT_NAME        + ", " +
                    PRODUCT_QUANT       + ", " +
                    PRODUCT_PRICE       + ", " +
                    SCRATCHED           + ", " +
                    PRODUCT_CATEGORY    + " FROM " +
                    SHOPPING_LIST_TABLE);

            // Remove older version database table
            db.execSQL("DROP TABLE " + SHOPPING_LIST_TABLE);

            db.execSQL(DATABASE_CREATE);

            // Insert data from temporary table
            db.execSQL("INSERT INTO " + SHOPPING_LIST_TABLE + " SELECT " +
                    PRODUCT_ID          + ", " +
                    PRODUCT_NAME        + ", " +
                    PRODUCT_QUANT       + ", " +
                    PRODUCT_PRICE       + ", " +
                    SCRATCHED           + ", " +
                    PRODUCT_CATEGORY    + ", " +
                    null                + " FROM " +
                    TEMP_SHOPPING_LIST_TABLE);

            // Remove temporary table
            db.execSQL("DROP TABLE " + TEMP_SHOPPING_LIST_TABLE);

            /** UPGRADE CONFIG_LIST_TABLE **/

            // Create an temporaty table that can store data of older version
            String TEMP_CREATE_CONFIG_LIST_TABLE = "CREATE TABLE " +
                    TEMP_CONFIG_LIST_TABLE                  + "(" +
                    CONFIG_LIST_ID                          + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CURRENT_BG_COLOR                        + " INT," +
                    CURRENT_FONT_COLOR                      + " INT," +
                    CURRENT_FONT_INSERT_COLOR               + " INT," +
                    CURRENT_TITLES_FONT_COLOR               + " INT," +
                    CURRENT_MESSAGE_FONT_COLOR              + " INT," +
                    CURRENT_PRODUCT_ADDED_FONT_COLOR        + " INT," +
                    CURRENT_PRODUCT_CANCELED_FONT_COLOR     + " INT," +
                    CURRENT_PRODUCT_SCRATCHED_FONT_COLOR    + " INT," +
                    CURRENT_CATEGORY_TITLE_FONT_COLOR       + " INT," +
                    CURRENT_CATEGORY_TITLE_BG_COLOR         + " INT);";
            db.execSQL(TEMP_CREATE_CONFIG_LIST_TABLE);

            // Insert data into temporary table from existing older version ConfigListTable
            db.execSQL("INSERT INTO " + TEMP_CONFIG_LIST_TABLE + " SELECT " +
                    CONFIG_LIST_ID                          + ", " +
                    CURRENT_BG_COLOR                        + ", " +
                    CURRENT_FONT_COLOR                      + ", " +
                    CURRENT_FONT_INSERT_COLOR               + ", " +
                    CURRENT_TITLES_FONT_COLOR               + ", " +
                    CURRENT_MESSAGE_FONT_COLOR              + ", " +
                    CURRENT_PRODUCT_ADDED_FONT_COLOR        + ", " +
                    CURRENT_PRODUCT_CANCELED_FONT_COLOR     + ", " +
                    CURRENT_PRODUCT_SCRATCHED_FONT_COLOR    + ", " +
                    CURRENT_CATEGORY_TITLE_FONT_COLOR       + ", " +
                    CURRENT_CATEGORY_TITLE_BG_COLOR         + " FROM " +
                    CONFIG_LIST_TABLE);

            // Remove older version ConfigListTable
            db.execSQL("DROP TABLE " + CONFIG_LIST_TABLE);

            db.execSQL(DATABASE_CONFIG_LIST_CREATE);

            // Insert data from temporary table
            db.execSQL("INSERT INTO " + CONFIG_LIST_TABLE + " SELECT " +
                    CONFIG_LIST_ID                          + ", " +
                    CURRENT_BG_COLOR                        + ", " +
                    CURRENT_FONT_COLOR                      + ", " +
                    CURRENT_FONT_INSERT_COLOR               + ", " +
                    CURRENT_TITLES_FONT_COLOR               + ", " +
                    CURRENT_MESSAGE_FONT_COLOR              + ", " +
                    CURRENT_PRODUCT_ADDED_FONT_COLOR        + ", " +
                    CURRENT_PRODUCT_CANCELED_FONT_COLOR     + ", " +
                    CURRENT_PRODUCT_SCRATCHED_FONT_COLOR    + ", " +
                    CURRENT_CATEGORY_TITLE_FONT_COLOR       + ", " +
                    CURRENT_CATEGORY_TITLE_BG_COLOR         + " FROM " +
                    TEMP_CONFIG_LIST_TABLE);

            // Remove temporary table
            db.execSQL("DROP TABLE " + TEMP_CONFIG_LIST_TABLE);

            /** UPGRADE SAVE_LIST_TABLE **/
            // Create an temporaty table that can store data of older version
            String TEMP_CREATE_SAVE_LIST_TABLE  = "CREATE TABLE " +
                    TEMP_SAVE_LIST_TABLE         + "(" +
                    LIST_ID                      + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    LIST_NAME                    + " TEXT," +
                    LIST_PRODUCT_NAME            + " TEXT," +
                    LIST_PRODUCT_QUANT           + " TEXT," +
                    LIST_PRODUCT_PRICE           + " TEXT," +
                    LIST_PRODUCT_SCRATCHED       + " TEXT," +
                    LIST_PRODUCT_CATEGORY        + " TEXT);";
            db.execSQL(TEMP_CREATE_SAVE_LIST_TABLE);

            // Insert data into temporary table from existing older version SaveListTable
            db.execSQL("INSERT INTO " + TEMP_SAVE_LIST_TABLE + " SELECT " +
                    LIST_ID                      + ", " +
                    LIST_NAME                    + ", " +
                    LIST_PRODUCT_NAME            + ", " +
                    LIST_PRODUCT_QUANT           + ", " +
                    LIST_PRODUCT_PRICE           + ", " +
                    LIST_PRODUCT_SCRATCHED       + ", " +
                    LIST_PRODUCT_CATEGORY        + " FROM " +
                    SAVE_LIST_TABLE);

            // Remove older version SaveListTable
            db.execSQL("DROP TABLE " + SAVE_LIST_TABLE);

            db.execSQL(DATABASE_SAVE_LIST_CREATE);

            // Insert data from temporary table
            db.execSQL("INSERT INTO " + SAVE_LIST_TABLE + " SELECT " +
                    LIST_ID                      + ", " +
                    LIST_NAME                    + ", " +
                    LIST_PRODUCT_NAME            + ", " +
                    LIST_PRODUCT_QUANT           + ", " +
                    LIST_PRODUCT_PRICE           + ", " +
                    LIST_PRODUCT_SCRATCHED       + ", " +
                    LIST_PRODUCT_CATEGORY        + ", " +
                    null                         + " FROM " +
                    TEMP_SAVE_LIST_TABLE);

            // Remove temporary table
            db.execSQL("DROP TABLE " + TEMP_SAVE_LIST_TABLE);

            /** UPGRADE LISTS_NAMES_TABLE **/
            // Create an temporaty table that can store data of older version
            String TEMP_CREATE_LISTS_NAMES_TABLE  = "CREATE TABLE " +
                    TEMP_LISTS_NAMES_TABLE        + "(" +
                    LISTS_NAMES_ID               + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    LISTS_NAMES_NAME             + " TEXT);";
            db.execSQL(TEMP_CREATE_LISTS_NAMES_TABLE);

            // Insert data into temporary table from existing older version ListsNamesTable
            db.execSQL("INSERT INTO " + TEMP_LISTS_NAMES_TABLE + " SELECT " +
                    LISTS_NAMES_ID               + ", " +
                    LISTS_NAMES_NAME             + " FROM " +
                    LISTS_NAMES_TABLE);

            // Remove older version ListsNamesTable
            db.execSQL("DROP TABLE " + LISTS_NAMES_TABLE);

            db.execSQL(DATABASE_LISTS_NAMES);

            // Insert data from temporary table
            db.execSQL("INSERT INTO " + LISTS_NAMES_TABLE + " SELECT " +
                    LISTS_NAMES_ID               + ", " +
                    LISTS_NAMES_NAME             + " FROM " +
                    TEMP_LISTS_NAMES_TABLE);

            // Remove temporary table
            db.execSQL("DROP TABLE " + TEMP_LISTS_NAMES_TABLE);

            /** UPGRADE LIST_LOADED_TABLE **/
            // Create an temporaty table that can store data of older version
            String TEMP_CREATE_LIST_LOADED_TABLE  = "CREATE TABLE " +
                    TEMP_LIST_LOADED_TABLE        + "(" +
                    LIST_LOADED_ID                + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    LIST_LOADED_NAME              + " TEXT);";
            db.execSQL(TEMP_CREATE_LIST_LOADED_TABLE);

            // Insert data into temporary table from existing older version ListsLoadedTable
            db.execSQL("INSERT INTO " + TEMP_LIST_LOADED_TABLE + " SELECT " +
                    LIST_LOADED_ID                + ", " +
                    LIST_LOADED_NAME              + " FROM " +
                    LIST_LOADED_TABLE);

            // Remove older version ListLoadedTable
            db.execSQL("DROP TABLE " + LIST_LOADED_TABLE);

            db.execSQL(DATABASE_LIST_LOADED);

            // Insert data from temporary table
            db.execSQL("INSERT INTO " + LIST_LOADED_TABLE + " SELECT " +
                    LIST_LOADED_ID               + ", " +
                    LIST_LOADED_NAME             + " FROM " +
                    TEMP_LIST_LOADED_TABLE);

            // Remove temporary table
            db.execSQL("DROP TABLE " + TEMP_LIST_LOADED_TABLE);

            db.execSQL(DATABASE_PANTRY);
        }

        private void updateVersion3(SQLiteDatabase db) {
            /** UPGRADE SHOPPING_LIST_TABLE **/

            // Create an temporaty table that can store data of older version
            String TEMP_CREATE_SHOPPING_LIST_TABLE = "CREATE TABLE " +
                    TEMP_SHOPPING_LIST_TABLE + "(" +
                    PRODUCT_ID               + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    PRODUCT_NAME             + " TEXT," +
                    PRODUCT_QUANT            + " TEXT," +
                    PRODUCT_PRICE            + " TEXT," +
                    SCRATCHED                + " TEXT," +
                    PRODUCT_CATEGORY         + " TEXT);";
            db.execSQL(TEMP_CREATE_SHOPPING_LIST_TABLE);

            // Insert data into temporary table from existing older version ShoppingListTable
            db.execSQL("INSERT INTO " + TEMP_SHOPPING_LIST_TABLE + " SELECT " +
                    PRODUCT_ID           + ", " +
                    PRODUCT_NAME         + ", " +
                    PRODUCT_QUANT        + ", " +
                    PRODUCT_PRICE        + ", " +
                    SCRATCHED            + ", " +
                    PRODUCT_CATEGORY     + " FROM " +
                    SHOPPING_LIST_TABLE);

            // Remove older version database table
            db.execSQL("DROP TABLE " + SHOPPING_LIST_TABLE);

            db.execSQL(DATABASE_CREATE);

            // Insert data from temporary table
            db.execSQL("INSERT INTO " + SHOPPING_LIST_TABLE + " SELECT " +
                    PRODUCT_ID        + ", " +
                    PRODUCT_NAME      + ", " +
                    PRODUCT_QUANT     + ", " +
                    PRODUCT_PRICE     + ", " +
                    SCRATCHED         + ", " +
                    PRODUCT_CATEGORY  + ", " +
                    null              + " FROM " +
                    TEMP_SHOPPING_LIST_TABLE);

            // Remove temporary table
            db.execSQL("DROP TABLE " + TEMP_SHOPPING_LIST_TABLE);

            /** UPGRADE CONFIG_LIST_TABLE **/

            // Create an temporaty table that can store data of older version
            String TEMP_CREATE_CONFIG_LIST_TABLE = "CREATE TABLE " +
                    TEMP_CONFIG_LIST_TABLE                  + "(" +
                    CONFIG_LIST_ID                          + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CURRENT_BG_COLOR                        + " INT," +
                    CURRENT_FONT_COLOR                      + " INT," +
                    CURRENT_FONT_INSERT_COLOR               + " INT," +
                    CURRENT_TITLES_FONT_COLOR               + " INT," +
                    CURRENT_MESSAGE_FONT_COLOR              + " INT," +
                    CURRENT_PRODUCT_ADDED_FONT_COLOR        + " INT," +
                    CURRENT_PRODUCT_CANCELED_FONT_COLOR     + " INT," +
                    CURRENT_PRODUCT_SCRATCHED_FONT_COLOR    + " INT," +
                    CURRENT_CATEGORY_TITLE_FONT_COLOR       + " INT," +
                    CURRENT_CATEGORY_TITLE_BG_COLOR         + " INT);";
            db.execSQL(TEMP_CREATE_CONFIG_LIST_TABLE);

            // Insert data into temporary table from existing older version ConfigListTable
            db.execSQL("INSERT INTO " + TEMP_CONFIG_LIST_TABLE + " SELECT " +
                    CONFIG_LIST_ID                          + ", " +
                    CURRENT_BG_COLOR                        + ", " +
                    CURRENT_FONT_COLOR                      + ", " +
                    CURRENT_FONT_INSERT_COLOR               + ", " +
                    CURRENT_TITLES_FONT_COLOR               + ", " +
                    CURRENT_MESSAGE_FONT_COLOR              + ", " +
                    CURRENT_PRODUCT_ADDED_FONT_COLOR        + ", " +
                    CURRENT_PRODUCT_CANCELED_FONT_COLOR     + ", " +
                    CURRENT_PRODUCT_SCRATCHED_FONT_COLOR    + ", " +
                    CURRENT_CATEGORY_TITLE_FONT_COLOR       + ", " +
                    CURRENT_CATEGORY_TITLE_BG_COLOR         + " FROM " +
                    CONFIG_LIST_TABLE);

            // Remove older version ConfigListTable
            db.execSQL("DROP TABLE " + CONFIG_LIST_TABLE);

            db.execSQL(DATABASE_CONFIG_LIST_CREATE);

            // Insert data from temporary table
            db.execSQL("INSERT INTO " + CONFIG_LIST_TABLE   + " SELECT " +
                    CONFIG_LIST_ID                          + ", " +
                    CURRENT_BG_COLOR                        + ", " +
                    CURRENT_FONT_COLOR                      + ", " +
                    CURRENT_FONT_INSERT_COLOR               + ", " +
                    CURRENT_TITLES_FONT_COLOR               + ", " +
                    CURRENT_MESSAGE_FONT_COLOR              + ", " +
                    CURRENT_PRODUCT_ADDED_FONT_COLOR        + ", " +
                    CURRENT_PRODUCT_CANCELED_FONT_COLOR     + ", " +
                    CURRENT_PRODUCT_SCRATCHED_FONT_COLOR    + ", " +
                    CURRENT_CATEGORY_TITLE_FONT_COLOR       + ", " +
                    CURRENT_CATEGORY_TITLE_BG_COLOR         + " FROM " +
                    TEMP_CONFIG_LIST_TABLE);

            // Remove temporary table
            db.execSQL("DROP TABLE " + TEMP_CONFIG_LIST_TABLE);

            db.execSQL(DATABASE_SAVE_LIST_CREATE);
            db.execSQL(DATABASE_LISTS_NAMES);
            db.execSQL(DATABASE_LIST_LOADED);
            db.execSQL(DATABASE_PANTRY);
        }
    }


    public DataHandler open () {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close () {
        dbHelper.close();
    }

    public Product addProduct (String pProductName, String pProductQuant, String pProductPrice,
                               String pScratched, String pProductCategory, String pProductUnit) {
        ContentValues cv = new ContentValues();
        cv.put(PRODUCT_NAME, pProductName);
        cv.put(PRODUCT_QUANT, pProductQuant);
        cv.put(PRODUCT_PRICE, pProductPrice);
        cv.put(SCRATCHED, pScratched);
        cv.put(PRODUCT_CATEGORY, pProductCategory);
        cv.put(PRODUCT_UNIT, pProductUnit);

        long productID = db.insert(SHOPPING_LIST_TABLE, null, cv);

        Cursor cursor = db.query(SHOPPING_LIST_TABLE, PRODUCT_COLUMNS, PRODUCT_ID + " = " + productID, null, null, null, null);
        cursor.moveToFirst();

        Product product = parseProduct(cursor);
        cursor.close();

        return product;
    }

    public void updateProduct (Product pProduct, String pNewProductName, String pNewProductQuant,
                               String pNewProductPrice, String pNewScratched, String pNewProductCategory,
                               String pNewProductUnit) {
        ContentValues cv = new ContentValues();
        cv.put(PRODUCT_NAME, pNewProductName);
        cv.put(PRODUCT_QUANT, pNewProductQuant);
        cv.put(PRODUCT_PRICE, pNewProductPrice);
        cv.put(SCRATCHED, pNewScratched);
        cv.put(PRODUCT_CATEGORY, pNewProductCategory);
        cv.put(PRODUCT_UNIT, pNewProductUnit);

        long productId = pProduct.getID();

        db.update(SHOPPING_LIST_TABLE, cv, PRODUCT_ID + " = " + productId, null);
    }

    public Product parseProduct(Cursor pCursor) {
        Product product = new Product();
        product.setID((pCursor.getInt(0)));
        product.setProductName(pCursor.getString(1));
        product.setProductQuant(pCursor.getString(2));
        product.setProductPrice(pCursor.getString(3));
        product.setScratched(pCursor.getString(4));
        product.setProductCategory(pCursor.getString(5));
        product.setProductUnit(pCursor.getString(6));
        return product;
    }

    public Cursor returnData () {
        return db.query(SHOPPING_LIST_TABLE, PRODUCT_COLUMNS, PRODUCT_ID, null, null, null, null);
    }

    public void addList (String pListName, String pProductName, String pProductQuant, String pProductPrice,
                         String pScratched, String pProductCategory, String pProductUnit) {
        ContentValues cv = new ContentValues();
        cv.put(LIST_NAME, pListName);
        cv.put(LIST_PRODUCT_NAME, pProductName);
        cv.put(LIST_PRODUCT_QUANT, pProductQuant);
        cv.put(LIST_PRODUCT_PRICE, pProductPrice);
        cv.put(LIST_PRODUCT_SCRATCHED, pScratched);
        cv.put(LIST_PRODUCT_CATEGORY, pProductCategory);
        cv.put(LIST_PRODUCT_UNIT, pProductUnit);

        long listId = db.insert(SAVE_LIST_TABLE, null, cv);

        db.query(SAVE_LIST_TABLE, SAVE_LIST_COLUMNS, LIST_ID + " = " + listId, null, null, null, null);
    }

    public void addList(String pListName, ListModel pListModel) {
        ContentValues cv = new ContentValues();
        cv.put(LIST_NAME, pListName);
        cv.put(LIST_PRODUCT_NAME, pListModel.getProductName());
        cv.put(LIST_PRODUCT_QUANT, pListModel.getProductQuant());
        cv.put(LIST_PRODUCT_PRICE, pListModel.getProductPrice());
        cv.put(LIST_PRODUCT_SCRATCHED, pListModel.getProductScratched());
        cv.put(LIST_PRODUCT_CATEGORY, pListModel.getProductCategory());
        cv.put(LIST_PRODUCT_UNIT, pListModel.getProductUnit());

        long listId = db.insert(SAVE_LIST_TABLE, null, cv);

        db.query(SAVE_LIST_TABLE, SAVE_LIST_COLUMNS, LIST_ID + " = " + listId, null, null, null, null);
    }

    public void renameList(String pOldName, String pNewName) {
        Boolean listNameChanged = false;
        Cursor cursor = returnSaveListData();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ListModel listModel = parseListModel(cursor);
            if(listModel.getListName().equals(pOldName)) {
                deleteListModel(listModel);
                addList(pNewName, listModel);
                if(!listNameChanged) {
                    listNameChanged = true;
                }
            }
            cursor.moveToNext();
        }
        cursor.close();

        if (listNameChanged) {
            cursor = returnListNameData();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ListName listName = parseListName(cursor);
                if(listName.getName().equals(pOldName)) {
                    deleteListName(listName);
                    addListName(pNewName);
                    break;
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
    }

    public ListModel parseListModel(Cursor pCursor) {
        ListModel listModel = new ListModel();
        listModel.setListId(pCursor.getInt(0));
        listModel.setListName(pCursor.getString(1));
        listModel.setProductName(pCursor.getString(2));
        listModel.setProductQuant(pCursor.getString(3));
        listModel.setProductPrice(pCursor.getString(4));
        listModel.setProductScratched(pCursor.getString(5));
        listModel.setProductCategory(pCursor.getString(6));
        listModel.setProductUnit(pCursor.getString(7));
        return listModel;
    }

    public void deleteListModel (ListModel pListModel) {
        db.delete(SAVE_LIST_TABLE, LIST_ID + " = " + pListModel.getId(), null);
    }

    public Cursor returnSaveListData() {
        return db.query(SAVE_LIST_TABLE, SAVE_LIST_COLUMNS, LIST_ID, null, null, null, null);
    }

    public void addListName (String pListName) {
        ContentValues cv = new ContentValues();
        cv.put(LISTS_NAMES_NAME, pListName);

        long listNameId = db.insert(LISTS_NAMES_TABLE, null, cv);

        db.query(LISTS_NAMES_TABLE, LISTS_NAMES_COLUMNS, LISTS_NAMES_ID + " = " + listNameId, null, null, null, null);
    }

    public void deleteListName(ListName pListName) {
        db.delete(LISTS_NAMES_TABLE, LISTS_NAMES_ID + " = " + pListName.getId(), null);
    }

    public ListName parseListName(Cursor pCursor) {
        ListName listName = new ListName();
        listName.setId(pCursor.getInt(0));
        listName.setName(pCursor.getString(1));
        return listName;
    }

    public Cursor returnListNameData() {
        return db.query(LISTS_NAMES_TABLE, LISTS_NAMES_COLUMNS, LISTS_NAMES_ID, null, null, null, null);
    }

    public void addListLoaded (String pListName) {
        ContentValues cv = new ContentValues();
        cv.put(LIST_LOADED_NAME, pListName);

        long listLoadedId = db.insert(LIST_LOADED_TABLE, null, cv);

        db.query(LIST_LOADED_TABLE, LIST_LOADED_COLUMNS, LIST_LOADED_ID + " = " + listLoadedId, null, null, null, null);
    }

    public Cursor returnListLoaded() {
        return db.query(LIST_LOADED_TABLE, LIST_LOADED_COLUMNS, LIST_LOADED_ID, null, null, null, null);
    }

    public String getListLoaded() {
        String listLoadedName = "Nova Lista";
        Cursor cursor = returnListLoaded();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listLoadedName = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();
        return listLoadedName;
    }

    public void deleteListLoaded() {
        Cursor cursor = returnListLoaded();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long listLoadedId = cursor.getInt(0);
            db.delete(LIST_LOADED_TABLE, LIST_LOADED_ID + " = " + listLoadedId, null);
            cursor.moveToNext();
        }
        cursor.close();
    }

    public Cursor returnConfigData () {
        return db.query(CONFIG_LIST_TABLE, CONFIG_LIST_COLUMNS, CONFIG_LIST_ID, null, null, null, null);
    }

    public ConfigData parseConfigData(Cursor pCursor) {
        ConfigData configData = new ConfigData();
        configData.setId((pCursor.getInt(0)));
        configData.setCurrentBgColor(pCursor.getInt(1));
        configData.setCurrentFontColor(pCursor.getInt(2));
        configData.setCurrentInsertFontColor(pCursor.getInt(3));
        configData.setCurrentTitlesFontColor(pCursor.getInt(4));
        configData.setCurrentMessageFontColor(pCursor.getInt(5));
        configData.setCurrentProductAddedFontColor(pCursor.getInt(6));
        configData.setCurrentProductCanceledFontColor(pCursor.getInt(7));
        configData.setCurrentProductScratchedFontColor(pCursor.getInt(8));
        configData.setCurrentCategoryTitleFontColor(pCursor.getInt(9));
        configData.setCurrentCategoryTitleBgColor(pCursor.getInt(10));
        return configData;
    }

    public void addConfigData(int pCurrentBgColor, int pCurrentFontColor, int pCurrentInsertFontColor,
                              int pCurrentTitlesFontColor, int pCurrentMessageFontColor, int pCurrentProductAddedFontColor,
                              int pCurrentProductCanceledFontColor, int pCurrentProductScratchedFontColor,
                              int pCurrentCategoryTitleFontColor, int pCurrentCategoryTitleBgColor) {
        ContentValues cv = new ContentValues();
        cv.put(CURRENT_BG_COLOR, pCurrentBgColor);
        cv.put(CURRENT_FONT_COLOR, pCurrentFontColor);
        cv.put(CURRENT_FONT_INSERT_COLOR, pCurrentInsertFontColor);
        cv.put(CURRENT_TITLES_FONT_COLOR, pCurrentTitlesFontColor);
        cv.put(CURRENT_MESSAGE_FONT_COLOR, pCurrentMessageFontColor);
        cv.put(CURRENT_PRODUCT_ADDED_FONT_COLOR, pCurrentProductAddedFontColor);
        cv.put(CURRENT_PRODUCT_CANCELED_FONT_COLOR, pCurrentProductCanceledFontColor);
        cv.put(CURRENT_PRODUCT_SCRATCHED_FONT_COLOR, pCurrentProductScratchedFontColor);
        cv.put(CURRENT_CATEGORY_TITLE_FONT_COLOR, pCurrentCategoryTitleFontColor);
        cv.put(CURRENT_CATEGORY_TITLE_BG_COLOR, pCurrentCategoryTitleBgColor);

        /*long configID = */db.insert(CONFIG_LIST_TABLE, null, cv);

//        Cursor cursor = db.query(CONFIG_LIST_TABLE, CONFIG_LIST_COLUMNS, CONFIG_LIST_ID + " = " + configID, null, null, null, null);
//        cursor.moveToFirst();
//        cursor.close();
    }

    public void deleteConfigData() {
        db = dbHelper.getWritableDatabase();
        Cursor cursor = returnConfigData();
        cursor.moveToFirst();
        int rows = cursor.getCount();
        if (rows > 0) {
            int i = 0;
            do {
                ConfigData configData = parseConfigData(cursor);
                db.delete(CONFIG_LIST_TABLE, CONFIG_LIST_ID + " = " + configData.getId(), null);
                cursor.moveToNext();
                i++;
            }
            while (i < rows);
        }
        cursor.close();
    }

    public void deleteProduct (Product pProduct) {
        long productID = pProduct.getID();

        db.delete(SHOPPING_LIST_TABLE, PRODUCT_ID + " = " + productID, null);
    }

    public void deleteList() {
        Cursor cursor = returnData();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = parseProduct(cursor);
            deleteProduct(product);
            cursor.moveToNext();
        }
        cursor.close();
    }

    public Cursor returnPantryData () {
        return db.query(PANTRY_TABLE, PANTRY_COLUMNS, PANTRY_ID, null, null, null, null);
    }

    public void addProductOnPantryData(PantryRow pProduct) {
        ContentValues cv = new ContentValues();
        cv.put(PANTRY_PRODUCT_NAME, pProduct.getProductName());
        cv.put(PANTRY_PRODUCT_QUANT, pProduct.getProductQuant());
        cv.put(PANTRY_PRODUCT_UNIT, pProduct.getProductUnit());
        cv.put(PANTRY_PRODUCT_CATEGORY, pProduct.getProductCategory());

        long pantryId = db.insert(PANTRY_TABLE, null, cv);

        db.query(PANTRY_TABLE, PANTRY_COLUMNS, PANTRY_ID + " = " + pantryId, null, null, null, null);
    }

    public void deleteProductOnPantryData (PantryRow pProduct) {
        long productID = pProduct.getID();

        db.delete(PANTRY_TABLE, PANTRY_ID + " = " + productID, null);
    }

    public PantryRow parseProductOnPantryData(Cursor pCursor) {
        PantryRow product = new PantryRow(context, pCursor.getString(1), pCursor.getString(2),
                pCursor.getString(3), pCursor.getString(4));
        product.setID(pCursor.getInt(0));
        return product;
    }

    public void updateProductOnPantry(PantryRow pProduct, String pNewName, String pNewQuant, String pNewUnit, String pNewCategory) {
        ContentValues cv = new ContentValues();
        cv.put(PANTRY_PRODUCT_NAME, pNewName);
        cv.put(PANTRY_PRODUCT_QUANT, pNewQuant);
        cv.put(PANTRY_PRODUCT_UNIT, pNewUnit);
        cv.put(PANTRY_PRODUCT_CATEGORY, pNewCategory);

        long productId = pProduct.getID();

        db.update(PANTRY_TABLE, cv, PANTRY_ID + " = " + productId, null);
    }
}