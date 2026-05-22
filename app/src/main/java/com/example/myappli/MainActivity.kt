package com.example.myappli

// Standard Android imports for UI components and activity lifecycle
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

/**
 * MainActivity - Main screen of the CalcApp calculator.
 *
 * This activity handles all user interaction with the calculator interface.
 * It uses a custom expression parser to evaluate mathematical expressions
 * typed through the on-screen buttons, supporting +, -, *, /, ^ and
 * functions such as sqrt, sin, cos, and tan.
 */
class MainActivity : ComponentActivity() {

    // TextView that displays the current expression and result
    var tvRes: TextView? = null

    /**
     * Called when the activity is first created.
     * Sets up the layout and binds the result display TextView.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load the main calculator layout
        setContentView(R.layout.layout)

        // Bind the result display to its view element
        tvRes = findViewById(R.id.tvRes)
    }

    /**
     * Handles all button presses from the calculator layout.
     * This method is referenced via android:onClick="calcular" in the XML layout.
     *
     * @param view The button that was pressed.
     */
    fun calcular(view: View) {
        val boton = view as Button
        val textoBoton = boton.text.toString()

        // Append the pressed button's value to the current display text
        val concatenar = tvRes?.text.toString() + textoBoton

        // Remove any leading zeros from the resulting string
        val concatenarSinCeros = quitarCerosIzquirda(concatenar)

        when (textoBoton) {
            "=" -> {
                // Evaluate the current expression and display the result
                var resultado = 0.0
                try {
                    resultado = eval(tvRes?.text.toString())
                    tvRes?.text = resultado.toString()
                } catch (e: Exception) {
                    // If the expression is invalid, show the error message
                    tvRes?.text = e.toString()
                }
            }
            "AC" -> {
                // Clear the display and reset to zero
                tvRes?.text = "0"
            }
            else -> {
                // Append the button value to the current display
                tvRes?.text = concatenarSinCeros
            }
        }
    }

    /**
     * Removes leading zeros from a numeric string.
     * For example, "007" becomes "7", and "0.5" stays "0.5".
     *
     * @param str The string to process.
     * @return The string with leading zeros removed.
     */
    fun quitarCerosIzquirda(str: String): String {
        var i = 0
        // Find the index of the first non-zero character
        while (i < str.length && str[i] == '0') i++
        val sb = StringBuffer(str)
        // Remove all characters before the first non-zero character
        sb.replace(0, i, "")
        return sb.toString()
    }

    /**
     * Evaluates a mathematical expression string and returns the result as a Double.
     *
     * Supports the following operations:
     *   - Addition (+), Subtraction (-), Multiplication (*), Division (/)
     *   - Exponentiation (^)
     *   - Parentheses for grouping
     *   - Functions: sqrt, sin, cos, tan (angles in degrees)
     *
     * Grammar rules used by the parser:
     *   expression = term | expression `+` term | expression `-` term
     *   term       = factor | term `*` factor | term `/` factor
     *   factor     = `+` factor | `-` factor | `(` expression `)`
     *              | number | functionName factor | factor `^` factor
     *
     * @param str The mathematical expression to evaluate.
     * @return The numeric result of the expression.
     * @throws RuntimeException if the expression contains unexpected characters or unknown functions.
     */
    fun eval(str: String): Double {
        return object : Any() {

            // Current position in the expression string
            var pos = -1

            // Current character being read (as an integer code point)
            var ch = 0

            /** Advances to the next character in the expression string. */
            fun nextChar() {
                ch = if (++pos < str.length) str[pos].code else -1
            }

            /**
             * Skips whitespace and checks whether the current character matches [charToEat].
             * If it matches, advances to the next character and returns true.
             *
             * @param charToEat The character code to match.
             * @return True if the character matched and was consumed, false otherwise.
             */
            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.code) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            /**
             * Entry point for parsing. Reads the first character and parses
             * the full expression, throwing an error if there are leftover characters.
             *
             * @return The evaluated result of the full expression.
             */
            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            /**
             * Parses addition and subtraction (lowest precedence).
             *
             * @return The result of the evaluated expression.
             */
            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    when {
                        eat('+'.code) -> x += parseTerm() // Addition
                        eat('-'.code) -> x -= parseTerm() // Subtraction
                        else -> return x
                    }
                }
            }

            /**
             * Parses multiplication and division (medium precedence).
             *
             * @return The result of the evaluated term.
             */
            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    when {
                        eat('*'.code) -> x *= parseFactor() // Multiplication
                        eat('/'.code) -> x /= parseFactor() // Division
                        else -> return x
                    }
                }
            }

            /**
             * Parses a factor: a number, a unary operator, a parenthesized expression,
             * a named function call, or an exponentiation.
             *
             * @return The evaluated value of the factor.
             */
            fun parseFactor(): Double {
                // Handle unary plus and minus
                if (eat('+'.code)) return parseFactor()  // Unary plus
                if (eat('-'.code)) return -parseFactor() // Unary minus

                var x: Double
                val startPos = pos

                when {
                    // Parenthesized sub-expression
                    eat('('.code) -> {
                        x = parseExpression()
                        eat(')'.code)
                    }

                    // Numeric literal (integer or decimal)
                    ch >= '0'.code && ch <= '9'.code || ch == '.'.code -> {
                        while (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) nextChar()
                        x = str.substring(startPos, pos).toDouble()
                    }

                    // Named function: sqrt, sin, cos, tan
                    ch >= 'a'.code && ch <= 'z'.code -> {
                        while (ch >= 'a'.code && ch <= 'z'.code) nextChar()
                        val func = str.substring(startPos, pos)
                        x = parseFactor()
                        x = when (func) {
                            "sqrt" -> Math.sqrt(x)
                            "sin"  -> Math.sin(Math.toRadians(x))
                            "cos"  -> Math.cos(Math.toRadians(x))
                            "tan"  -> Math.tan(Math.toRadians(x))
                            else   -> throw RuntimeException("Unknown function: $func")
                        }
                    }

                    else -> throw RuntimeException("Unexpected: " + ch.toChar())
                }

                // Handle exponentiation (highest precedence, right-associative)
                if (eat('^'.code)) x = Math.pow(x, parseFactor())

                return x
            }

        }.parse()
    }
}
