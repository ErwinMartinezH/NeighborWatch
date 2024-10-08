<?php
include 'db_connection.php'; // Incluir el archivo de conexión

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Obtener datos de la solicitud
    $phoneNumber = isset($_POST['numero_telefono']) ? $_POST['numero_telefono'] : '';
    $password = isset($_POST['contraseña']) ? $_POST['contraseña'] : '';

    // Consulta para verificar el usuario
    $sql = "SELECT * FROM cuentas WHERE numero_telefono = ? AND contraseña = ?";
    $stmt = $conn->prepare($sql);

    if ($stmt === false) {
        die(json_encode(array('success' => false, 'message' => 'Error en la preparación de la consulta')));
    }

    $stmt->bind_param("ss", $phoneNumber, $password);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $user = $result->fetch_assoc();
        echo json_encode(array('success' => true, 'message' => 'Inicio de sesión exitoso', 'user' => $user));
    } else {
        echo json_encode(array('success' => false, 'message' => 'Número de teléfono o contraseña incorrectos'));
    }

    $stmt->close();
} else {
    echo json_encode(array('success' => false, 'message' => 'Método no permitido, utilice POST.'));
}

$conn->close();
?>
