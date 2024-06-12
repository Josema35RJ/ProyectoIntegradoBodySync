document.addEventListener("DOMContentLoaded", function() {
    function mostrarErrores() {
        var errorMessage = "${flash.error}";
        var errorMessagesElement = document.getElementById("errorMessages");
        if (errorMessagesElement && errorMessage !== "") {
            errorMessagesElement.innerHTML = errorMessage;
            errorMessagesElement.style.display = "block";
            var modal = new bootstrap.Modal(document.getElementById('addInstructorModal'));
            modal.show();
        }
    }

    function fillEditInstructorModal(button) {
        var id = button.getAttribute('data-id');
        var firstName = button.getAttribute('data-firstName');
        var lastName = button.getAttribute('data-lastName');
        var dni = button.getAttribute('data-dni');
        var username = button.getAttribute('data-username');
        var biography = button.getAttribute('data-biography');
        var city = button.getAttribute('data-city');
        var province = button.getAttribute('data-province');
        var postalCode = button.getAttribute('data-postalCode');
        var role = button.getAttribute('data-role');

        document.getElementById('editInstructorId').value = id;
        document.getElementById('editFirstName').value = firstName;
        document.getElementById('editLastName').value = lastName;
        document.getElementById('editDni').value = dni;
        document.getElementById('editUsername').value = username;
        document.getElementById('editBiography').value = biography;
        document.getElementById('editCity').value = city;
        document.getElementById('editProvince').value = province;
        document.getElementById('editPostalCode').value = postalCode;
        document.getElementById('editRole').value = role;

        // Mostrar el modal después de llenar los datos
        var modal = new bootstrap.Modal(document.getElementById('editInstructorModal'));
        modal.show();
    }

    // Asignar la función de rellenar y mostrar el modal a todos los botones de editar
    var editButtons = document.querySelectorAll('.edit-button');
    editButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            fillEditInstructorModal(button);
        });
    });

    mostrarErrores();

    const filterInput = document.getElementById('filterInput');
    const activeInstructorCards = document.getElementById('activeInstructorCards');
    const inactiveInstructorCards = document.getElementById('inactiveInstructorCards');

    if (filterInput && activeInstructorCards && inactiveInstructorCards) {
        filterInput.addEventListener('input', function() {
            const filterValue = filterInput.value.toLowerCase();
            filterInstructors(activeInstructorCards, filterValue);
            filterInstructors(inactiveInstructorCards, filterValue);
        });

        function filterInstructors(container, filterValue) {
            const cards = container.getElementsByClassName('card');
            Array.from(cards).forEach(card => {
                const cardText = card.textContent.toLowerCase();
                card.style.display = cardText.includes(filterValue) ? '' : 'none';
            });
        }
    }

    const activos = 10; // Reemplaza con el número real de instructores activos
    const inactivos = 5; // Reemplaza con el número real de instructores inactivos

    var instructorStatusData = {
        labels: ["Activos", "Inactivos"],
        datasets: [{
            data: [activos, inactivos],
            backgroundColor: ["#36a2eb", "#ff6384"]
        }]
    };

    var ctxStatus = document.getElementById("instructorStatusChart") ? document.getElementById("instructorStatusChart").getContext("2d") : null;
    if (ctxStatus) {
        var instructorStatusChart;
        if (instructorStatusChart) {
            instructorStatusChart.destroy();
        }
        instructorStatusChart = new Chart(ctxStatus, {
            type: 'pie',
            data: instructorStatusData
        });
    }

    var ctxPerformance = document.getElementById('instructorPerformanceChart') ? document.getElementById('instructorPerformanceChart').getContext('2d') : null;
    if (ctxPerformance) {
        var instructorNames = [];
        var classesTaught = [];

        var instructorCards = document.querySelectorAll('.card');
        instructorCards.forEach(function(card) {
            var name = card.querySelector('.card-title').textContent.trim();
            var classesCount = parseInt(card.querySelector('.card-text').textContent.replace('Clases: ', '').trim());
            instructorNames.push(name);
            classesTaught.push(classesCount);
        });

        var performanceData = {
            labels: instructorNames,
            datasets: [{
                label: 'Clases Impartidas',
                data: classesTaught,
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        };

        var instructorPerformanceChart;
        if (instructorPerformanceChart) {
            instructorPerformanceChart.destroy();
        }
        instructorPerformanceChart = new Chart(ctxPerformance, {
            type: 'bar',
            data: performanceData,
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Cantidad de Clases'
                        }
                    },
                    x: {
                        title: {
                            display: true,
                            text: 'Instructor'
                        }
                    }
                }
            }
        });
    }
});
// Script para llenar el modal de edición con los datos del instructor seleccionado
			function fillEditInstructorModal(button) {
				const instructorData = $(button).data();
				$('#editInstructorId').val(instructorData.id);
				$('#editFirstName').val(instructorData.firstname);
				$('#editLastName').val(instructorData.lastname);
				$('#editDni').val(instructorData.dni);
				$('#editUsername').val(instructorData.username);
				$('#editBiography').val(instructorData.biography);
				$('#editCity').val(instructorData.city);
				$('#editProvince').val(instructorData.province);
				$('#editPostalCode').val(instructorData.postalcode);
				$('#editRole').val(instructorData.role);
				$('#editPassword').val(instructorData.password);
			}

			// Script para el gráfico de estado de los instructores
			const ctx = document.getElementById('instructorStatusChart')
					.getContext('2d');
			const instructorStatusChart = new Chart(ctx, {
				type : 'pie',
				data : {
					labels : [ 'Activos', 'Inactivos' ],
					datasets : [ {
						label : 'Estado de Instructores',
						data : [ 10, 5 ], // Reemplaza con los datos reales
						backgroundColor : [ '#28a745', '#dc3545' ],
					} ]
				},
				options : {
					responsive : true,
					plugins : {
						legend : {
							position : 'top',
						},
						title : {
							display : true,
							text : 'Estado de los Instructores'
						}
					}
				}
			});