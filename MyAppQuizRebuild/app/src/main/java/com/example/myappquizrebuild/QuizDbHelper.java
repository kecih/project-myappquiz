package com.example.myappquizrebuild;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myappquizrebuild.QuizContract.*;

import java.util.ArrayList;


public class QuizDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "QuizMania.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Presiden pertama Indonesia?",
                "A. Soekarno", "B. Soeharto", "C. BJ.Habibie", 1, Question.DIFFICULTY_EASY);
        addQuestion(q1);
        Question q2 = new Question("Pendiri perusahaan Microsoft?",
                "A. Steve Jobs", "B. Warren Buffet", "C. Bill Gates", 3, Question.DIFFICULTY_EASY);
        addQuestion(q2);
        Question q3 = new Question("Kapan hari kemerdekaan Indonesia dirayakan?",
                "A. 1 Juni", "B. 17 Agustus", "C. 29 November", 2, Question.DIFFICULTY_EASY);
        addQuestion(q3);
        Question q4 = new Question("Kapan hari sumpah pemuda dirayakan?",
                "A. 28 Oktober", "B. 2 November", "C. 30 Maret", 1, Question.DIFFICULTY_EASY);
        addQuestion(q4);
        Question q5 = new Question("Kapan hari kelahiran Pancasila diadakan?",
                "A. 30 Januari", "B. 1 Juni", "C. 29 Juli", 2, Question.DIFFICULTY_EASY);
        addQuestion(q5);
        Question q6 = new Question("Dimanakah Ibukota Nigeria",
                "A. Lagos", "B. Abuja", "C. Nairobi", 2, Question.DIFFICULTY_HARD);
        addQuestion(q6);
        Question q7 = new Question("Kapan ASEAN di dirikan?",
                "A. 8 Agustus 1967", "B. 8 Desember 1967", "C. 8 November 1987", 1, Question.DIFFICULTY_HARD);
        addQuestion(q7);
        Question q8 = new Question("Dimanakah Piala dunia pertama kali diadakan?",
                "A. Indonesia", "B. Argentina", "C. Uruguay", 3, Question.DIFFICULTY_HARD);
        addQuestion(q8);
        Question q9 = new Question("Siapakah penemu pulpen?",
                "A. Lazlo Biro", "B. Alexander Ryu", "C. John J. Loud", 3, Question.DIFFICULTY_HARD);
        addQuestion(q9);
        Question q10 = new Question("Bagaimanakah Adolf Hitler meninggal?",
                "A. Bunuh diri", "B. Kecelakaan", "C. Diracuni", 1, Question.DIFFICULTY_HARD);
        addQuestion(q10);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}

