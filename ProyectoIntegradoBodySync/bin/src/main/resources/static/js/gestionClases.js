document.addEventListener('DOMContentLoaded', function() {
	// Función para obtener los datos de asistencia y popularidad
	function fetchChartData(url, chartId, labelKey, dataKey, backgroundColor, borderColor) {
		fetch(url)
			.then(response => {
				if (!response.ok) {
					throw new Error('Error al obtener los datos: ' + response.statusText);
				}
				return response.json();
			})
			.then(data => {
				if (!Array.isArray(data)) {
					throw new Error('Los datos no son un array');
				}
				const labels = data.map(item => item[labelKey]);
				const chartData = data.map(item => item[dataKey]);

				var ctx = document.getElementById(chartId).getContext('2d');
				var chart = new Chart(ctx, {
					type: 'bar',
					data: {
						labels: labels,
						datasets: [{
							label: labelKey.charAt(0).toUpperCase() + labelKey.slice(1), // Capitalizar la primera letra
							data: chartData,
							backgroundColor: backgroundColor,
							borderColor: borderColor,
							borderWidth: 1
						}]
					},
					options: {
						scales: {
							y: {
								beginAtZero: true
							}
						}
					}
				});
			})
			.catch(error => console.error(error));
	}

	// Obtener datos de asistencia y popularidad
	fetchChartData('/auth/gymOwner/asistencia', 'attendanceChart', 'nombre', 'asistencia', 'rgba(0, 123, 255, 0.5)', 'rgba(0, 123, 255, 1)');
	fetchChartData('/auth/gymOwner/popularidad', 'popularityChart', 'nombre', 'popularidad', 'rgba(255, 99, 132, 0.2)', 'rgba(255, 99, 132, 1)');

	// Función para determinar el estado de la clase
	function determinarEstadoClase(horarioInicio, horarioFin, diasSemana) {
		var ahora = new Date();
		var diaSemanaActual = ahora.getDay();
		var horaActual = ahora.getHours() + ahora.getMinutes() / 60;

		var [horaInicio, minutoInicio] = horarioInicio.split(':').map(Number);
		var [horaFin, minutoFin] = horarioFin.split(':').map(Number);
		var inicioEnHoras = horaInicio + minutoInicio / 60;
		var finEnHoras = horaFin + minutoFin / 60;

		if (diasSemana.includes(diaSemanaActual.toString())) {
			if (horaActual >= inicioEnHoras && horaActual < finEnHoras) {
				return 'enCurso';
			} else if (horaActual < inicioEnHoras) {
				return 'proxima';
			}
		}
		return 'finalizada';
	}

	// Manejar las tarjetas de clase
	var tarjetasClase = document.querySelectorAll('.class-card');
	tarjetasClase.forEach(function(tarjeta) {
		var claseId = tarjeta.getAttribute('data-id');
		var horarioInicio = tarjeta.getAttribute('data-horario-inicio');
		var horarioFin = tarjeta.getAttribute('data-horario-fin');
		var diasSemana = tarjeta.getAttribute('data-dias-semana').split(',');
		var estadoClase = determinarEstadoClase(horarioInicio, horarioFin, diasSemana);
		var cardHeader = tarjeta.querySelector('.card-header .status');
		var cardBody = tarjeta.querySelector('.card-body');
		if (estadoClase === 'enCurso') {
			cardHeader.classList.add('status', 'enCurso');
			cardHeader.textContent = 'En Curso';
			cardBody.classList.add('active');
		} else if (estadoClase === 'proxima') {
			cardHeader.classList.add('status', 'proxima');
			cardHeader.textContent = 'Próxima';
			cardBody.classList.add('upcoming');
		} else if (estadoClase === 'finalizada') {
			cardHeader.classList.add('status', 'finalizada');
			cardHeader.textContent = 'Finalizada';
			cardBody.classList.add('finished');
		}
	});

	// Event listener para el botón de editar
	document.querySelectorAll('.edit-btn').forEach(button => {
		button.addEventListener('click', function(event) {
			var classId = event.target.getAttribute('data-class-id');
			var form = document.querySelector('#editExistingClassForm');
			var newActionUrl = '/auth/gymOwner/updateClass/' + classId;
			form.setAttribute('action', newActionUrl);
		});
	});

	// Función para obtener los instructores y actualizar el select
	function getInstructors() {
		fetch("/auth/gymOwner/instructors")
			.then(response => response.json())
			.then(data => {
				const select = document.getElementById("newClassInstructor");
				select.innerHTML = ""; // Limpiar opciones existentes
				data.forEach(instructor => {
					const option = document.createElement("option");
					option.value = instructor.id;
					option.text = instructor.firstName + " " + instructor.lastName;
					select.appendChild(option);
				});
			})
			.catch(error => console.error("Error al obtener los instructores:", error));
	}

	// Llamar a la función al cargar la página y al abrir el modal de crear clase
	document.addEventListener("DOMContentLoaded", getInstructors);
	$('#createNewClassModal').on('shown.bs.modal', getInstructors);
});

// Función para activar una clase
function activateClass(classId) {
	fetch('/auth/gymOwner/activateClass/' + classId, {
		method: 'GET'
	})
		.then(response => response.json())
		.then(data => {
			if (data.success) {
				// Actualizar la interfaz según el éxito o fracaso
				console.log(data.message);
			} else {
				// Manejar el error
				console.error(data.error);
			}
		})
		.catch(error => console.error("Error al activar la clase:", error));
}

// Función para desactivar una clase
function deactivateClass(classId) {
	fetch('/auth/gymOwner/deactivateClass/' + classId, {
		method: 'GET'
	})
		.then(response => response.json())
		.then(data => {
			if (data.success) {
				// Actualizar la interfaz según el éxito o fracaso
				console.log(data.message);
			} else {
				// Manejar el error
				console.error(data.error);
			}
		})
		.catch(error => console.error("Error al desactivar la clase:", error));
}

// Función para editar una clase
function editClass(claseId) {
	var formData = new FormData(document.getElementById('editClassForm'));
	// Aquí debes recoger todos los datos del formulario y enviarlos a tu servidor

	fetch('/api/editClass/' + claseId, {
		method: 'POST',
		body: formData
	})
		.then(response => response.json())
		.then(data => {
			// Manejar la respuesta del servidor
			if (data.success) {
				// Actualizar la interfaz de usuario o mostrar un mensaje de éxito
				console.log('Clase editada con éxito');
			} else {
				// Mostrar un mensaje de error
				console.error('Error al editar la clase');
			}
		})
		.catch(error => {
			console.error('Error:', error);
		});
}

// Event listener para el botón de editar
document.querySelector('.edit-btn').addEventListener('click', function(event) {
	// Obtiene el ID de la clase del atributo data-class-id del botón
	var classId = event.target.getAttribute('data-class-id');

	// Encuentra el formulario
	var form = document.querySelector('#editExistingClassForm');

	// Construye la nueva URL de la acción del formulario
	var newActionUrl = '/auth/gymOwner/updateClass/' + classId;

	// Establece la nueva URL de la acción del formulario
	form.setAttribute('action', newActionUrl);
});

// Event listener para activar/desactivar clase
$('#toggleClassModal').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget); // Botón que activó el modal
	var classId = button.data('class-id'); // Extrae la información de los atributos data-*
	var classActive = button.data('class-active');
	var modal = $(this);

	if (classActive) {
		modal.find('.modal-body #toggleClassMessage').text('¿Estás seguro de que quieres desactivar esta clase?');
	} else {
		modal.find('.modal-body #toggleClassMessage').text('¿Estás seguro de que quieres activar esta clase?');
	}
});
$('#editExistingClassModal').on('show.bs.modal', function(event) {
    var button = $(event.relatedTarget); // Botón que activó el modal
    var classId = button.data('class-id'); // Extrae el ID de la clase
    var className = button.data('class-name'); // Extrae la información de los atributos data-*
    var classDescription = button.data('class-description');
    var classInstructorId = button.data('class-instructor'); // Extrae el ID del instructor
    var classTime = button.data('class-time');
    var classCapacity = button.data('class-capacity');
    var classStartDate = button.data('class-start-date');
    var classEndDate = button.data('class-end-date');
    var classDuration = button.data('class-duration'); // Extrae la duración de la clase
    var classDaysOfWeek = button.data('class-days-of-week'); // Extrae los días de la semana

    var modal = $(this);
    modal.find('.modal-body #editClassName').val(className);
    modal.find('.modal-body #editClassDescription').val(classDescription);
    modal.find('.modal-body #editClassTime').val(classTime);
    modal.find('.modal-body #editClassMaximumCapacity').val(classCapacity);
    modal.find('.modal-body #editClassStartDate').val(classStartDate);
    modal.find('.modal-body #editClassEndDate').val(classEndDate);
    modal.find('.modal-body #editClassDuration').val(classDuration); // Establece la duración en el campo del formulario
    modal.find('.modal-body #editClassDaysOfWeek').val(classDaysOfWeek); // Establece los días de la semana

    var select = modal.find('.modal-body #editClassInstructor');

    // Limpia el select
    select.empty();

    // Haz una solicitud AJAX para obtener los datos de los instructores
    $.ajax({
        url: '/auth/gymOwner/instructors',
        method: 'GET',
        dataType: 'json',
        success: function(instructors) {
            // Añade los instructores al select
            instructors.forEach(function(instructor) {
                var option = new Option(instructor.name, instructor.id);
                if (instructor.id === classInstructorId) {
                    option.selected = true;
                }
                select.append(option);
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus, errorThrown);
            if (jqXHR.responseText.startsWith('<')) {
                console.error('La respuesta no es un JSON válido, parece ser HTML.');
            }
        }
    });

    modal.find('#editExistingClassForm').attr('action', '/auth/gymOwner/updateClass/' + classId);
});

document.querySelectorAll('.toggle-btn').forEach(btn => {
    btn.addEventListener('click', function(event) {
        event.preventDefault(); // Prevenir el comportamiento predeterminado del enlace
        var classId = this.getAttribute('data-class-id');
        var isActive = this.getAttribute('data-class-active') === 'true'; // Determinar si la clase está activa o no
        var action = isActive ? 'deactivateClass' : 'activateClass'; // Determinar la acción según el estado actual
        // Enviar una solicitud GET al endpoint correspondiente para activar/desactivar la clase
        fetch(`/auth/gymOwner/${action}/${classId}`)
            .then(response => {
                if (response.ok) {
                    // Recargar la página después de activar/desactivar la clase con éxito
                    window.location.reload();
                } else {
                    // Manejar errores si los hay
                }
            })
            .catch(error => console.error('Error al cambiar el estado de la clase:', error));
    });
});

document.querySelectorAll('.view-feedback-btn').forEach(btn => {
	btn.addEventListener('click', function(event) {
		event.preventDefault(); // Prevenir el comportamiento predeterminado del enlace
		var classId = this.getAttribute('data-class-id');

		// Realizar una solicitud AJAX para obtener el feedback
		fetch(`/auth/gymOwner/viewFeedback/${classId}`)
			.then(response => {
				if (response.ok) {
					return response.json();
				} else {
					console.error('Error al obtener el feedback de la clase:', response.statusText);
				}
			})
			.then(feedbacks => {
				if (feedbacks) {
					renderFeedback(feedbacks);
				}
			})
			.catch(error => console.error('Error al obtener el feedback de la clase:', error));
	});
});

function renderFeedback(feedbacks) {
	const feedbackContainer = document.getElementById('feedbackContainer');
	feedbackContainer.innerHTML = ''; // Clear previous feedback

	feedbacks.forEach(feedback => {
		const feedbackItem = document.createElement('div');
		feedbackItem.className = 'feedback-item mb-3';

		feedbackItem.innerHTML = `
            <div class="card">
                <div class="card-body">
                    <div class="star-rating" data-rating="${feedback.rating}">
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                    </div>
                    <p class="card-text mt-2">${feedback.comments}</p>
                </div>
            </div>
        `;

		feedbackContainer.appendChild(feedbackItem);
	});

	updateStarRatings();
}

function updateStarRatings() {
	document.querySelectorAll('.star-rating').forEach(starRating => {
		const rating = parseFloat(starRating.getAttribute('data-rating'));
		const stars = starRating.querySelectorAll('.fa-star');
		stars.forEach((star, index) => {
			if (index < rating) {
				star.classList.add('filled');
			}
		});
	});
}

// Llamar a la función para actualizar las estrellas en caso de que el contenido ya esté cargado
updateStarRatings();
