package com.example.najmedinezahra.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.najmedinezahra.data.model.Question
import com.example.najmedinezahra.data.model.QuizCategory
import com.example.najmedinezahra.data.model.Difficulty

@Database(entities = [Question::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tunisia_heritage_database"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            generateSampleQuestions().forEach { question ->
                db.execSQL(
                    """INSERT INTO questions VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""",
                    arrayOf(
                        question.id,
                        question.category,
                        question.title,
                        question.imageResId,
                        question.optionA,
                        question.optionB,
                        question.optionC,
                        question.optionD,
                        question.correctAnswer,
                        question.difficulty,
                        question.keyFact
                    )
                )
            }
        }
    }
}

fun generateSampleQuestions(): List<Question> {
    val questions = mutableListOf<Question>()
    var id = 1

    questions.addAll(listOf(
        Question(
            id = id++,
            category = QuizCategory.ROMAN_HERITAGE.name,
            title = "Which is the 3rd largest Roman amphitheater in the world?",
            imageResId = "ic_el_jem",
            optionA = "Colosseum in Rome",
            optionB = "El Jem Amphitheater",
            optionC = "Arles Amphitheater",
            optionD = "Pula Amphitheater",
            correctAnswer = 1,
            difficulty = Difficulty.EASY.name,
            keyFact = "El Jem Amphitheater is the 3rd largest Roman amphitheater in the world"
        ),
        Question(
            id = id++,
            category = QuizCategory.ROMAN_HERITAGE.name,
            title = "El Jem is located in which Tunisian city?",
            imageResId = "ic_el_jem",
            optionA = "Tunis",
            optionB = "Sfax",
            optionC = "El Jem",
            optionD = "Kairouan",
            correctAnswer = 2,
            difficulty = Difficulty.EASY.name,
            keyFact = "El Jem Amphitheater is located in the city of El Jem"
        ),
        Question(
            id = id++,
            category = QuizCategory.ROMAN_HERITAGE.name,
            title = "When was the El Jem Amphitheater built?",
            imageResId = "ic_el_jem",
            optionA = "1st century AD",
            optionB = "2nd century AD",
            optionC = "3rd century AD",
            optionD = "4th century AD",
            correctAnswer = 2,
            difficulty = Difficulty.EASY.name,
            keyFact = "El Jem Amphitheater was built in the 3rd century AD"
        ),
        Question(
            id = id++,
            category = QuizCategory.ROMAN_HERITAGE.name,
            title = "What was the original name of Tunis in Roman times?",
            imageResId = "ic_carthage",
            optionA = "Carthago",
            optionB = "Tingis",
            optionC = "Colonia Aurelia Carthago",
            optionD = "Utica",
            correctAnswer = 2,
            difficulty = Difficulty.MEDIUM.name,
            keyFact = "Tunis was called Colonia Aurelia Carthago in Roman times"
        ),
        Question(
            id = id++,
            category = QuizCategory.ROMAN_HERITAGE.name,
            title = "Which Roman emperor ordered the construction of El Jem Amphitheater?",
            imageResId = "ic_el_jem",
            optionA = "Hadrian",
            optionB = "Gordian III",
            optionC = "Augustus",
            optionD = "Severus",
            correctAnswer = 1,
            difficulty = Difficulty.MEDIUM.name,
            keyFact = "Gordian III ordered the construction of El Jem Amphitheater"
        ),
        Question(
            id = id++,
            category = QuizCategory.ROMAN_HERITAGE.name,
            title = "How many spectators could El Jem Amphitheater hold?",
            imageResId = "ic_el_jem",
            optionA = "20,000",
            optionB = "35,000",
            optionC = "50,000",
            optionD = "75,000",
            correctAnswer = 2,
            difficulty = Difficulty.MEDIUM.name,
            keyFact = "El Jem could hold approximately 35,000 spectators"
        ),
        Question(
            id = id++,
            category = QuizCategory.ROMAN_HERITAGE.name,
            title = "What games were held in El Jem Amphitheater?",
            imageResId = "ic_el_jem",
            optionA = "Chariot races only",
            optionB = "Gladiator combats",
            optionC = "Public speeches",
            optionD = "Religious ceremonies",
            correctAnswer = 1,
            difficulty = Difficulty.HARD.name,
            keyFact = "Gladiator combats and wild animal hunts were held in El Jem"
        ),
        Question(
            id = id++,
            category = QuizCategory.ROMAN_HERITAGE.name,
            title = "Which Roman province was Tunisia part of?",
            imageResId = "ic_carthage",
            optionA = "Africa Proconsularis",
            optionB = "Numidia",
            optionC = "Mauritania",
            optionD = "Tripolitania",
            correctAnswer = 0,
            difficulty = Difficulty.HARD.name,
            keyFact = "Tunisia was part of the Roman province of Africa Proconsularis"
        ),
        Question(
            id = id++,
            category = QuizCategory.ROMAN_HERITAGE.name,
            title = "What was the significance of Carthage in Roman times?",
            imageResId = "ic_carthage",
            optionA = "Military fortress",
            optionB = "Religious center",
            optionC = "Major trade hub",
            optionD = "Agricultural center",
            correctAnswer = 2,
            difficulty = Difficulty.HARD.name,
            keyFact = "Carthage became a major Roman trade hub and economic center"
        ),
        Question(
            id = id++,
            category = QuizCategory.ROMAN_HERITAGE.name,
            title = "In which century did Roman control of Tunisia end?",
            imageResId = "ic_el_jem",
            optionA = "5th century",
            optionB = "6th century",
            optionC = "7th century",
            optionD = "8th century",
            correctAnswer = 2,
            difficulty = Difficulty.HARD.name,
            keyFact = "Roman control of Tunisia ended in the 7th century with Arab conquest"
        )
    ))

    return questions
}
