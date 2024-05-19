// Función para mostrar los mensajes de error en el modal
    function mostrarErrores() {
        var errorMessage = "${flash.error}";
        if (errorMessage !== "") {
            document.getElementById("errorMessages").innerHTML = errorMessage;
            document.getElementById("errorMessages").style.display = "block";
            // Si hay errores, se abre automáticamente el modal
            var modal = new bootstrap.Modal(document.getElementById('addInstructorModal'));
            modal.show();
        }
    }
    // Llamar a la función cuando la página cargue
    window.onload = mostrarErrores;