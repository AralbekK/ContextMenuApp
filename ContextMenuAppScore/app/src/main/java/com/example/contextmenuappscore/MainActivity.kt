package com.example.contextmenuappscore

import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // Поле ввода для ввода оценки
    private lateinit var scoreInput: EditText
    // Кнопка для генерации случайного числа
    private lateinit var generateRandomButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализируем тлубар и устанавливаем заголовок
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Оценка за урок"

        // Инициализируем поле ввода и добавим его для контекстного меню
        scoreInput = findViewById(R.id.scoreInput)
        registerForContextMenu(scoreInput)

        // Инициализируем кнопку и добавим функционал для генерации рандомного числа
        generateRandomButton = findViewById(R.id.generateRandomButton)
        generateRandomButton.setOnClickListener {
            val randomValue = Random.nextInt(1, 51)
            scoreInput.setText(randomValue.toString())
        }
    }

    // Создаем контекстное меню
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        // Устанавливаем заголовок контекстного меню, если menu не равно null
        menu?.setHeaderTitle(getString(R.string.context_menu_header))

        // Проверяем, что v не равно null перед добавлением пунктов меню
        v?.let {
            // Добавляем пункт "Цветовое качество"
            menu?.add(0, it.id, 0, "Цветовое качество")
            // Добавляем пункт "Выход из приложения"
            menu?.add(0, it.id, 1, "Выход из приложения")
            // Добавляем пункт "Случайное число"
            menu?.add(0, it.id, 2, "Случайное число")
        }
    }

    // Обработка выбора в контекстном меню
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.title) {
            "Цветовое качество" -> {
                applyColor() // Применяем цвет к полю ввода
                true
            }
            "Выход из приложения" -> {
                finish() // Завершаем работу приложения
                true
            }
            "Случайное число" -> {
                val randomValue = Random.nextInt(1, 51)
                scoreInput.setText(randomValue.toString())
                applyColor() // Применяем цвет к полю ввода для случайного числа
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    // Применение цвета к полю ввода в зависимости от введенной оценки
    private fun applyColor() {
        val score = try {
            scoreInput.text.toString().toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Введите корректную оценку", Toast.LENGTH_SHORT).show()
            return
        }

        val color = when (score) {
            in 1..10 -> Color.parseColor("#FF0000") // Красный
            in 11..20 -> Color.parseColor("#FFA500") // Оранжевый
            in 21..30 -> Color.parseColor("#FFD700") // Желтый
            in 31..40 -> Color.parseColor("#008000") // Зеленый
            in 41..50 -> Color.parseColor("#0000FF") // Синий
            else -> {
                Toast.makeText(this, "Оценка должна быть от 1 до 50", Toast.LENGTH_SHORT).show()
                return
            }
        }
        scoreInput.setBackgroundColor(color)
    }
}
