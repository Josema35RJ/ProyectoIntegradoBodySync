 document.addEventListener('DOMContentLoaded', function() {
            fetch('/api/asistencia')
                .then(response => response.json())
                .then(dataAsistencia => {
                    const labelsAsistencia = dataAsistencia.map(clase => clase.nombre);
                    const datosAsistencia = dataAsistencia.map(clase => clase.asistencia);

                    var ctxAsistencia = document.getElementById('attendanceChart').getContext('2d');
                    var chartAsistencia = new Chart(ctxAsistencia, {
                        type: 'bar',
                        data: {
                            labels: labelsAsistencia,
                            datasets: [{
                                label: 'Asistencia',
                                data: datosAsistencia,
                                backgroundColor: 'rgba(0, 123, 255, 0.5)',
                                borderColor: 'rgba(0, 123, 255, 1)',
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
                .catch(error => console.error('Error al obtener los datos de asistencia:', error));

            fetch('/api/popularidad')
                .then(response => response.json())
                .then(dataPopularidad => {
                    const labelsPopularidad = dataPopularidad.map(clase => clase.nombre);
                    const datosPopularidad = dataPopularidad.map(clase => clase.popularidad);

                    var ctxPopularidad = document.getElementById('popularityChart').getContext('2d');
                    var chartPopularidad = new Chart(ctxPopularidad, {
                        type: 'bar',
                        data: {
                            labels: labelsPopularidad,
                            datasets: [{
                                label: 'Popularidad',
                                data: datosPopularidad,
                                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                                borderColor: 'rgba(255, 99, 132, 1)',
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
                .catch(error => console.error('Error al obtener los datos de popularidad:', error));
        });
        document.addEventListener('DOMContentLoaded', function() {
            // Obtener todas las tarjetas de clase en la página
            var tarjetasClase = document.querySelectorAll('.class-card');

            tarjetasClase.forEach(function(tarjeta) {
                // Suponiendo que cada tarjeta tiene un atributo 'data-id' que corresponde al ID de la clase
                var claseId = tarjeta.getAttribute('data-id');
                var horarioInicio = tarjeta.getAttribute('data-horario-inicio');
                var horarioFin = tarjeta.getAttribute('data-horario-fin');
                var diasSemana = tarjeta.getAttribute('data-dias-semana').split(',');

                // Llamar a una función que determina el estado de la clase basado en el horario
                var estadoClase = determinarEstadoClase(horarioInicio, horarioFin, diasSemana);

                // Seleccionar elementos dentro de la tarjeta para actualizar
                var cardHeader = tarjeta.querySelector('.card-header .status');
                var cardBody = tarjeta.querySelector('.card-body');

                // Actualizar la tarjeta de clase basada en el estado
                if (estadoClase === 'enCurso') {
                    cardHeader.classList.add('bg-success');
                    cardHeader.textContent = 'En Curso';
                    cardBody.classList.add('active');
                } else if (estadoClase === 'proxima') {
                    cardHeader.classList.add('bg-warning');
                    cardHeader.textContent = 'Próxima';
                    cardBody.classList.add('upcoming');
                } else if (estadoClase === 'finalizada') {
                    cardHeader.classList.add('bg-danger');
                    cardHeader.textContent = 'Finalizada';
                    cardBody.classList.add('finished');
                }
            });
        });

        function determinarEstadoClase(horarioInicio, horarioFin, diasSemana) {
            var ahora = new Date();
            var diaSemanaActual = ahora.getDay();
            var horaActual = ahora.getHours() + ahora.getMinutes() / 60;

            // Convertir horarios de inicio y fin a horas
            var [horaInicio, minutoInicio] = horarioInicio.split(':').map(Number);
            var [horaFin, minutoFin] = horarioFin.split(':').map(Number);
            var inicioEnHoras = horaInicio + minutoInicio / 60;
            var finEnHoras = horaFin + minutoFin / 60;

            // Verificar si hoy la clase está programada
            if (diasSemana.includes(diaSemanaActual.toString())) {
                if (horaActual >= inicioEnHoras && horaActual < finEnHoras) {
                    return 'enCurso';
                } else if (horaActual < inicioEnHoras) {
                    return 'proxima';
                }
            }

            return 'finalizada';
        }
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
                if(data.success) {
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
        // Escucha el evento de clic en el botón
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
