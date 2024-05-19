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
