const membershipApiUrl = '/auth/gymUsers/membershipStats';
		const attendanceApiUrl = '/auth/gymUsers/attendanceStats';

		// Usamos fetch para obtener los datos de la API
		fetch(membershipApiUrl)
		    .then(response => response.json())
		    .then(membershipData => {
		        // Una vez que tenemos los datos, los usamos para crear la gráfica de membresía
		        new Chart(document.getElementById('membershipChart'), {
		            type: 'bar',
		            data: {
		                labels: Object.keys(membershipData),
		                datasets: [{
		                    label: 'Número de Miembros',
		                    data: Object.values(membershipData),
		                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
		                    borderColor: 'rgba(75, 192, 192, 1)',
		                    borderWidth: 1
		                }]
		            },
		            options: { /* opciones de la gráfica */ }
		        });
		    });

		fetch(attendanceApiUrl)
		    .then(response => response.json())
		    .then(attendanceData => {
		        // Hacemos lo mismo para la gráfica de asistencia
		        new Chart(document.getElementById('attendanceChart'), {
		            type: 'bar',
		            data: {
		                labels: Object.keys(attendanceData),
		                datasets: [{
		                    label: 'Asistencia',
		                    data: Object.values(attendanceData),
		                    backgroundColor: 'rgba(153, 102, 255, 0.2)',
		                    borderColor: 'rgba(153, 102, 255, 1)',
		                    borderWidth: 1
		                }]
		            },
		            options: { /* opciones de la gráfica */ }
		        });
		    });
		    // Función para filtrar miembros
function filterMembers() {
  var inputName, inputEmail, inputCity, filterName, filterEmail, filterCity, memberCards, card, i, name, email, city;
  
  // Obtener los valores de los filtros
  inputName = document.getElementById('nameFilter').value.toUpperCase();
  inputEmail = document.getElementById('emailFilter').value.toUpperCase();
  inputCity = document.getElementById('cityFilter').value.toUpperCase();
  
  // Obtener las tarjetas de miembros
  memberCards = document.getElementById('memberCards');
  card = memberCards.getElementsByClassName('member-card');

  // Iterar sobre las tarjetas de miembros y ocultar las que no coinciden
  for (i = 0; i < card.length; i++) {
    name = card[i].getElementsByClassName('card-title')[0].innerText.toUpperCase();
    email = card[i].getElementsByClassName('card-text')[0].getElementsByTagName('span')[1].innerText.toUpperCase();
    city = card[i].getElementsByClassName('card-text')[0].getElementsByTagName('span')[3].innerText.toUpperCase();

    // Comprobar si la tarjeta coincide con los filtros
    if (name.indexOf(inputName) > -1 && email.indexOf(inputEmail) > -1 && city.indexOf(inputCity) > -1) {
      card[i].style.display = "";
    } else {
      card[i].style.display = "none";
    }
  }
}

// Event listeners para los filtros
document.getElementById('nameFilter').addEventListener('input', filterMembers);
document.getElementById('emailFilter').addEventListener('input', filterMembers);
document.getElementById('cityFilter').addEventListener('input', filterMembers);
// Función para mostrar la notificación de logro
    function mostrarNotificacionLogro() {
        var toast = new bootstrap.Toast(document.getElementById('achievementToast'));
        toast.show();
    }

    // Verificar periódicamente si se ha alcanzado un nuevo logro
    setInterval(function() {
        // Obtener todas las tarjetas de miembros activos
        var tarjetasMiembrosActivos = document.querySelectorAll('.tab-pane.active .member-card');

        // Iterar sobre cada tarjeta de miembro activo
        tarjetasMiembrosActivos.forEach(function(tarjeta) {
            // Obtener el atributo data-logro de la tarjeta
            var logro = tarjeta.getAttribute('data-logro');

            // Verificar si el usuario ha alcanzado un nuevo logro
            if (logro && logro === 'true') {
                // Si el usuario ha alcanzado un nuevo logro, mostrar la notificación
                mostrarNotificacionLogro();
            }
        });
    }, 30000); // 30000 milisegundos = 30 segundos
    // Función para generar la paginación
function generatePagination(totalItems, itemsPerPage) {
    // Eliminar la paginación anterior si existe
    $('#pagination').empty();

    // Calcular el número total de páginas
    var totalPages = Math.ceil(totalItems / itemsPerPage);

    // Agregar botón 'Anterior'
    $('#pagination').append('<li class="page-item"><a class="page-link" href="#" onclick="previousPage()">Anterior</a></li>');

    // Agregar botones de página
    for (var i = 1; i <= totalPages; i++) {
        $('#pagination').append('<li class="page-item"><a class="page-link" href="#" onclick="changePage(' + i + ')">' + i + '</a></li>');
    }

    // Agregar botón 'Siguiente'
    $('#pagination').append('<li class="page-item"><a class="page-link" href="#" onclick="nextPage()">Siguiente</a></li>');
}

// Funciones para cambiar de página
function changePage(page) {
    // Implementa lógica para mostrar la página 'page'
}

// Variables para controlar la paginación
let currentPage = 1;
const itemsPerPage = 6; // Cambia este valor según la cantidad de elementos que quieras mostrar por página
const totalItems = 30; // Ejemplo: total de elementos a paginar

// Función para mostrar la página anterior
function previousPage() {
    if (currentPage > 1) {
        currentPage--;
        showPage(currentPage);
    }
}

// Función para mostrar la página siguiente
function nextPage() {
    const totalPages = Math.ceil(totalItems / itemsPerPage);
    if (currentPage < totalPages) {
        currentPage++;
        showPage(currentPage);
    }
}

// Función para mostrar los elementos de la página actual
function showPage(page) {
    // Calcula el rango de elementos a mostrar en la página actual
    const startIndex = (page - 1) * itemsPerPage;
    const endIndex = Math.min(startIndex + itemsPerPage, totalItems);

}

// Llama a esta función para generar la paginación cuando la página cargue
$(document).ready(function () {
    // Supongamos que 'totalItems' es el número total de miembros y 'itemsPerPage' es la cantidad de tarjetas que deseas mostrar por página
    generatePagination(totalItems, itemsPerPage);
});
