import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUpScreen(navController: NavController) {
    
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "CREATE ACCOUNT",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4CAF50) // ગ્રીન કલર
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ૨. ઈમેલ બોક્સ
        OutlinedTextField(
            value = emailInput,
            onValueChange = { emailInput = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // ૩. પાસવર્ડ બોક્સ
        OutlinedTextField(
            value = passwordInput,
            onValueChange = { passwordInput = it },
            label = { Text("Password (min 6 chars)") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // ૪. કન્ફર્મ પાસવર્ડ બોક્સ
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ૫. SIGN UP બટન (Firebase રજીસ્ટ્રેશન લોજિક)
        Button(
            onClick = {
                if (emailInput.isNotEmpty() && passwordInput.isNotEmpty()) {
                    if (passwordInput == confirmPassword) {
                        // Firebase માં નવો યુઝર બનાવો
                        auth.createUserWithEmailAndPassword(emailInput, passwordInput)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(context, "Account Created Successfully!", Toast.LENGTH_SHORT).show()
                                    // એકાઉન્ટ બન્યા પછી સીધા હોમ પેજ પર
                                    navController.navigate("home") {
                                        popUpTo("signup") { inclusive = true }
                                    }
                                } else {
                                    Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                    } else {
                        Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text("SIGN UP", color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // લોગિન પર પાછા જવા માટે
        TextButton(onClick = { navController.popBackStack() }) {
            Text("Already have an account? Login")
        }
    }
}
