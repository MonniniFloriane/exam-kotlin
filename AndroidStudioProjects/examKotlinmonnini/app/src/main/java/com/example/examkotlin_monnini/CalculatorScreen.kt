package com.example.examkotlin_monnini

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun CalculatorScreen() {
    //mutable
    var affichNbr by remember { mutableStateOf("0") }
    var operation by remember { mutableStateOf<String?>(null) }
    var firstOperation by remember { mutableDoubleStateOf(0.0) }

    //Fonctions

    //isDigit MAJ la variable affichNbr pour afficher le chiffre saisie
    fun isDigit(digit: Int) {
        affichNbr = if (affichNbr == "0") digit.toString() else affichNbr + digit.toString()
    }

    //isOperateur permet d'enregistrer un 1er nbr (float) puis un opérator
    fun isOperateur(operator: String) {
        firstOperation = affichNbr.toDouble()
        affichNbr = "0"
        operation = operator
    }

    //isEqual permet de calculer 2 nombre avec divers opérator en String tant qu'une opération est effectuer sinon l'operation est null
    fun isEqual() {
        val secondOperation = affichNbr.toDouble()
        affichNbr = when (operation) {
            "+" -> (firstOperation + secondOperation).toString()
            "-" -> (firstOperation - secondOperation).toString()
            "*" -> (firstOperation * secondOperation).toString()
            "/" -> (firstOperation / secondOperation).toString()
            "%" -> (firstOperation % secondOperation).toString()
            else -> affichNbr
        }
        operation = null
    }

    //isClear permet de remmettre les premiers enregistrement à zero
    fun isClear() {
        affichNbr = "0"
        firstOperation = 0.0
        operation = null
    }

    //DigitBtn permet de creer un btn qui affiche un chiffre
    @Composable
    fun DigitBtn(digit: Int, onClick: () -> Unit) {
        Button(onClick = onClick) {
            Text(digit.toString())
        }
    }

    //OperateurBtn permet de creer un btn qui affiche un String
    @Composable
    fun OperateurBtn(operation: String, onClick: () -> Unit) {
        Button(onClick = onClick) {
            Text(operation)
        }
    }

    //Affichage
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black), // Thème sombre
        contentAlignment = Alignment.Center // Centrer le contenu horizontalement et verticalement
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp), // Espacement vertical entre les éléments
            horizontalAlignment = Alignment.CenterHorizontally // Centrage horizontal
        ) {
            Text(
                text = affichNbr,
                //theme de base dans MaterialTheme
                style = MaterialTheme.typography.displayLarge.copy(color = Color.White), // Texte blanc pour le thème sombre
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                textAlign = TextAlign.End
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ligne pour les fonctions spéciales
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("%", "C").forEach { op ->
                        Button(
                            colors = ButtonDefaults.buttonColors(Color.Black),
                            onClick = { isOperateur(op) },
                            modifier = Modifier
                                .weight(1f)
                                .border(2.dp, Color.White, RoundedCornerShape(50.dp)) // Bordure blanche avec radius
                                .clip(RoundedCornerShape(20.dp)) // Appliquer le radius à l'ensemble du bouton
                        ) {
                            Text(op)
                        }
                    }
                }

                // Boutons numériques et opérateurs
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val digits = listOf(
                        listOf(7, 8, 9, "x"),
                        listOf(4, 5, 6, "-"),
                        listOf(1, 2, 3, "+"),
                        listOf(0, "=")
                    )

                    digits.forEach { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            row.forEach { item ->
                                val isOperator = item in listOf("+", "-", "x", "=") // Identifier les opérateurs
                                val buttonColor = when (item) {
                                    "=" -> Color.Magenta // Couleur spécifique pour "="
                                    in listOf("+", "-", "x") -> Color.Blue // Couleur pour les autres opérateurs
                                    else -> Color.DarkGray // Couleur par défaut pour les autres boutons
                                }

                                Button(
                                    onClick = {
                                        when (item) {
                                            is Int -> isDigit(item)
                                            "=" -> isEqual()
                                            "C" -> isClear()
                                            else -> isOperateur(item.toString())
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f),
                                    colors = ButtonDefaults.buttonColors(buttonColor)
                                ) {
                                    Text(item.toString(), color = Color.White) // Texte blanc pour tous les boutons
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}