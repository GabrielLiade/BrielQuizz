package com.example.brielquizz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import com.example.brielquizz.QuizzContract.*;

import java.util.ArrayList;
import java.util.List;


public class QuizzDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyAwesomeQuizz.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizzDbHelper instance;

    private SQLiteDatabase db;

    public QuizzDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizzDbHelper getInstance(Context context) {
        if(instance == null){
            instance = new QuizzDbHelper(context.getApplicationContext());
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";
        final String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE " +
                QuetionTable.TABLE_NAME + " ( " +
                QuetionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuetionTable.COLUMN_QUESTION + " TEXT, " +
                QuetionTable.COLUMN_OPTION1+ " TEXT, " +
                QuetionTable.COLUMN_OPTION2+ " TEXT, " +
                QuetionTable.COLUMN_OPTION3+ " TEXT, " +
                QuetionTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuetionTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuetionTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";
        db.execSQL((SQL_CREATE_CATEGORIES_TABLE));
        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        fillCategoriesTable();
        fillQuestionTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +QuetionTable.TABLE_NAME);
        onCreate(db);

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable(){
        Category c1 = new Category("Briel");
        insertCategory(c1);
        Category c2 = new Category("Glerbel");
        insertCategory(c2);
        Category c3 = new Category("PA");
        insertCategory(c3);

    }

    private void addCategory(Category category) {
        db = getWritableDatabase();
        insertCategory(category);
    }

    public void addCategories(List<Category> categories) {
        db = getWritableDatabase();
                for (Category category: categories) {
                    insertCategory(category);
                }
    }

    private void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionTable() {
        Question q1 = new Question("Quelle est la date d'anniversaire de Gabriel ?", "25 mars 2000", "25 février 2000", "6 mai 2002", 1, Category.BRIEL);
        insertQuestion(q1);
        Question q2 = new Question("Qui est la personne que Gabriel préfère à la Wsf ?", "Théo", "Clément", "Bruno Faurre", 3, Category.BRIEL);
        insertQuestion(q2);
        Question q3 = new Question("Quelle est la meilleure qualité de Gabriel ? ", "Son Flow", "Les deux", "Son Inteligence", 2, Category.BRIEL);
        insertQuestion(q3);
        Question q4 = new Question("Quel est le plat préféré de Gabriel", "Le poulet", "Le mcDO", "Pâte bolognaise", 1, Category.BRIEL);
        insertQuestion(q4);
        Question q5 = new Question("Comment Gabriel se fait-il appeler par ses amis ?", "Briel de Torse", "Rotor Truelle", "Mot aléatoire + briel", 3, Category.BRIEL);
        insertQuestion(q5);

        Question q6 = new Question("A quelle heure théo prévient quand il est en retard ?  ", "1h avant", "2h avant", "Jamais", 1, Category.GERBEL);
        insertQuestion(q6);
        Question q7 = new Question("Combien de fois Théo a-t-il vomit au Vietnam ?", "0 fois", "6 fois", "3 fois", 2, Category.GERBEL);
        insertQuestion(q7);
        Question q8 = new Question("Quelle est la chose la plus folle que Théo a pu faire ?", "Tomber en scoot au Vietnam", "Voler un poney", "Sa masterclass après avoir déposé sa copine", 3, Category.GERBEL);
        insertQuestion(q8);
        Question q9 = new Question("Quel est la principal caractéristique de Théo", "Insuportable", "Lourd (dmand à Anouk elle va tdiiire)", "Perfectioniste", 3, Category.GERBEL);
        insertQuestion(q9);
        Question q10 = new Question("Quel est l'idole de Théo", "Gabriel", "Clément", "GroPecCéo", 3, Category.GERBEL);
        insertQuestion(q10);

        Question q11 = new Question("Quel est l’expression préféré de PA ?", "Blarf", "La ché", "La Foudre", 1, Category.PA);
        insertQuestion(q11);
        Question q12 = new Question("Quel est la chose la plus attachante chez PA ?", "Son rire", "Sa personnalité", "Sa moula !", 1, Category.PA);
        insertQuestion(q12);
        Question q13 = new Question("Combien d’heure d’absence PA a-t-il cumulé ?", "30h", "15h", "On compte plus", 3, Category.PA);
        insertQuestion(q13);
        Question q14 = new Question("Qui est son principale acolyte ?", "Pilou", "Hector", "Les deux", 3, Category.PA);
        insertQuestion(q14);
        Question q15 = new Question("Combien de cocards a-t-il eu tout le long de la WSF", "5", "4", "2", 2, Category.PA);
        insertQuestion(q15);



    }

    public void addQuestion(Question question) {
        db = getWritableDatabase();
        insertQuestion(question);
    }

    public void addQuestions(List<Question> questions) {
        db = getWritableDatabase();
        for (Question question : questions) {
            insertQuestion(question);
        }
    }
    private void insertQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuetionTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuetionTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuetionTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuetionTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuetionTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuetionTable.COLUMN_CATEGORY_ID, question.getCategoryID());

        db.insert(QuetionTable.TABLE_NAME, null, cv);
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME )));
                categoryList.add(category);
            } while (c.moveToNext());

        }
        c.close();
        return categoryList;
    }

    public List<Question> getAllQuestion(int categoryId){
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuetionTable.TABLE_NAME, null);
        if(c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuetionTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuetionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuetionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuetionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuetionTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuetionTable.COLUMN_ANSWER_NR)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuetionTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestion(int categoryId){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuetionTable.COLUMN_CATEGORY_ID + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryId)};
        Cursor c = db.query(
                QuetionTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null

        );
        if(c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuetionTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuetionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuetionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuetionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuetionTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuetionTable.COLUMN_ANSWER_NR)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuetionTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
