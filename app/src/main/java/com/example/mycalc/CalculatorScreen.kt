package com.example.mycalc

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycalc.ui.theme.liquidGlass

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    val dynamicPrimary = MaterialTheme.colorScheme.primaryContainer
    val dynamicTertiary = MaterialTheme.colorScheme.tertiaryContainer

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        dynamicTertiary.copy(alpha = 0.40f),
                        MaterialTheme.colorScheme.surface
                    ),
                    radius = 1400f
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            // Стеклянный дисплей
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .liquidGlass(
                        shapeRadius = 32.dp,
                        tintColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = viewModel.expression.ifEmpty { "0" },
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = viewModel.result,
                        fontSize = 54.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // Клавиши калькулятора
            val buttons = listOf(
                listOf("C", "÷", "×", "DEL"),
                listOf("7", "8", "9", "-"),
                listOf("4", "5", "6", "+"),
                listOf("1", "2", "3", "="),
                listOf("0", ".", "", "")
            )

            buttons.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    row.forEach { symbol ->
                        if (symbol.isNotEmpty()) {
                            GlassCalcButton(
                                text = symbol,
                                modifier = Modifier
                                    .weight(if (symbol == "0") 2f else 1f)
                                    .aspectRatio(if (symbol == "0") 2.1f else 1f),
                                onClick = {
                                    when (symbol) {
                                        "C" -> viewModel.onAction(CalculatorAction.Clear)
                                        "DEL" -> viewModel.onAction(CalculatorAction.Delete)
                                        "=" -> viewModel.onAction(CalculatorAction.Calculate)
                                        "+" -> viewModel.onAction(CalculatorAction.Operation('+'))
                                        "-" -> viewModel.onAction(CalculatorAction.Operation('-'))
                                        "×" -> viewModel.onAction(CalculatorAction.Operation('×'))
                                        "÷" -> viewModel.onAction(CalculatorAction.Operation('÷'))
                                        "." -> viewModel.onAction(CalculatorAction.Decimal)
                                        else -> viewModel.onAction(CalculatorAction.Number(symbol))
                                    }
