$('#editExistingClassModal').on(
				'show.bs.modal',
				function(event) {
					var button = $(event.relatedTarget) // Botón que activó el modal
					var classId = button.data('class-id') // Extrae el ID de la clase
					var className = button.data('class-name') // Extrae la información de los atributos data-*
					var classDescription = button.data('class-description')
					var classInstructorId = button.data('class-instructor') // Extrae el ID del instructor
					var classTime = button.data('class-time')
					var classCapacity = button.data('class-capacity')
					var classStartDate = button.data('class-start-date')
					var classEndDate = button.data('class-end-date')
					var classDuration = button.data('class-duration') // Extrae la duración de la clase

					var modal = $(this)
					modal.find('.modal-body #existingClassName').val(className)
					modal.find('.modal-body #existingClassDescription').val(
							classDescription)
					modal.find('.modal-body #existingClassTime').val(classTime)
					modal.find('.modal-body #existingClassMaximumCapacity')
							.val(classCapacity)
					modal.find('.modal-body #existingClassStartDate').val(
							classStartDate)
					modal.find('.modal-body #existingClassEndDate').val(
							classEndDate)
					modal.find('.modal-body #existingClassDuration').val(
							classDuration) // Establece la duración en el campo del formulario

					var select = modal
							.find('.modal-body #existingClassInstructor')

					// Limpia el select
					select.empty();

					// Añade los instructores al select
					instructors
							.forEach(function(instructor) {
								var option = new Option(instructor.name,
										instructor.id);
								if (instructor.id === classInstructorId) {
									option.selected = true;
								}
								select.append(option);
							});

					modal.find('#editExistingClassForm').attr('action',
							'/auth/gymOwner/updateClass/' + classId)
				});
		$('#toggleClassModal')
				.on(
						'show.bs.modal',
						function(event) {
							var button = $(event.relatedTarget) // Botón que activó el modal
							var classId = button.data('class-id') // Extrae la información de los atributos data-*
							var classActive = button.data('class-active')

							var modal = $(this)
							if (classActive) {
								modal
										.find('.modal-body #toggleClassMessage')
										.text(
												'¿Estás seguro de que quieres desactivar esta clase?')
								modal.find('.modal-footer #confirmToggleLink')
										.attr(
												'href',
												'/auth/gymOwner/deactivateClass/'
														+ classId)
							} else {
								modal
										.find('.modal-body #toggleClassMessage')
										.text(
												'¿Estás seguro de que quieres activar esta clase?')
								modal.find('.modal-footer #confirmToggleLink')
										.attr(
												'href',
												'/auth/gymOwner/activateClass/'
														+ classId)
							}
						})
		function redirectToMembersPage(classId) {
			// Almacenar el ID de la clase en la sesión del usuario
			sessionStorage.setItem('classId', classId);
			// Redirigir a la página de gestión de miembros
			window.location.href = '/auth/gymInstructor/GestionMiembros';
		}