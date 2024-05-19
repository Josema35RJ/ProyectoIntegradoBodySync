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

function fillEditInstructorModal(button) {
	var id = button.getAttribute('data-id');
	var firstName = button.getAttribute('data-firstName');
	var lastName = button.getAttribute('data-lastName');
	var dni = button.getAttribute('data-dni');
	var username = button.getAttribute('data-email');
	var biography = button.getAttribute('data-biography');
	var city = button.getAttribute('data-city');
	var province = button.getAttribute('data-province');
	var postalCode = button.getAttribute('data-postalCode');
	var role = button.getAttribute('data-role');

	document.getElementById('editInstructorId').value = id;
	document.getElementById('editFirstName').value = firstName;
	document.getElementById('editLastName').value = lastName;
	document.getElementById('editDni').value = dni;
	document.getElementById('editEmail').value = username;
	document.getElementById('editBiography').value = biography;
	document.getElementById('editCity').value = city;
	document.getElementById('editProvince').value = province;
	document.getElementById('editPostalCode').value = postalCode;
	document.getElementById('role').value = role;
}

    var instructorStatusData = {
        labels: ["Activos", "Inactivos"],
        datasets: [{
            data: [/* Cantidad de instructores activos */, /* Cantidad de instructores inactivos */],
            backgroundColor: ["#36a2eb", "#ff6384"]
        }]
    };

    var ctx = document.getElementById("instructorStatusChart").getContext("2d");
    var myPieChart = new Chart(ctx, {
        type: 'pie',
        data: instructorStatusData
    });

