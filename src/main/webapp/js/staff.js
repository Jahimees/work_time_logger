{
    let staffCache;

    function loadStaff() {
        $.ajax({
            method: 'get',
            url: '/accounts',
            dataType: "json",
            async: false,
            success: (data) => {
                staffCache = data;
                console.log(data);
            },
            error: () => {
                callMessagePopup("Ошибка", "Невозможно загрузить данные о сотрудниках")
            }
        })
    }

    function updateAccountCache(account) {
        staffCache.forEach(acc => {
            if (acc.idAccount = account.idAccount) {
                acc.employerDetails = account.employerDetails;
                acc.role = account.role;
                acc.department = account.department;
                acc.position = account.position;
            }
        })
    }

    function findAccountInCache(idAccount) {
        let foundAccount;
        staffCache.forEach(account => {
            if (account.idAccount === idAccount) {
                foundAccount = account;
            }
        })

        return foundAccount;
    }

    function addToStaffCache(staff) {
        staffCache.push(staff);
    }

    function deleteFromStaffCache(idAccount) {
        let index;
        staffCache.forEach(account => {
            if (account.idAccount === idAccount) {
                index = staffCache.indexOf(account);
            }
        })
        staffCache.splice(index, 1);
    }

    function fillStaffTable() {
        const tableName = "staff"
        const $dataTable = $("#" + tableName + "_table");

        destroyAndInitDataTable(tableName, $dataTable)

        staffCache?.forEach(function (staff) {
            addRowToStaffDataTable(staff, tableName)
        })
    }

    function addRowToStaffDataTable(staff, tableName) {
        const firstName = staff.employerDetails?.firstName;
        const lastName = staff.employerDetails?.lastName;
        const departmentName = staff.department?.name;
        const positionName = staff.position?.name;
        const phone = staff.employerDetails?.phone;

        $("#" + tableName + "_table").DataTable().row.add([
            firstName ? firstName : "Не заполнено",
            lastName ? lastName : "Не заполнено",
            departmentName ? departmentName : "Не назначен",
            positionName ? positionName : "Не назначен",
            phone ? phone : "Неизвестен",
            "<div onclick='callConfirmDeleteAccount(" + staff.idAccount + ")' class='save-button'>Удалить</div>",
            "<div onclick='loadChangeAccountPopup(" + staff.idAccount + ")' class='save-button'>Открыть</div>"
        ]).draw();
    }

    function loadChangeAccountPopup(idAccount) {
        $("#changeAccountModal").modal('show');
        const chosenAccount = findAccountInCache(idAccount);
        fillAccountPopupSelectors(false)

        const $firstNameInput = $("#firstName-change-popup");
        const $lastNameInput = $("#lastName-change-popup");
        const $addressInput = $("#address-change-popup");
        const $aboutInput = $("#about-change-popup");
        const $phoneInput = $("#phone-change-popup");
        const $departmentSelect = $("#department-change-select-popup");
        const $positionSelect = $("#position-change-select-popup");
        const $roleSelect = $("#role-change-select-popup");

        $firstNameInput.val(chosenAccount.employerDetails.firstName);
        $lastNameInput.val(chosenAccount.employerDetails.lastName);
        $addressInput.val(chosenAccount.employerDetails.address);
        $aboutInput.val(chosenAccount.employerDetails.about);
        $phoneInput.val(chosenAccount.employerDetails.phone);
        $departmentSelect.val(chosenAccount.department.idDepartment);
        $positionSelect.val(chosenAccount.position.idPosition);
        $roleSelect.val(chosenAccount.role.idRole);

        $("#confirm-change-account-btn").unbind();
        $("#confirm-change-account-btn").on('click', () => {
            let flag = true;

            if ($firstNameInput.val().trim().length < 1 || $firstNameInput.val().trim().length > 30) {
                flag = false;
                $("#error-change-firstname-lbl").show();
            } else {
                $("#error-change-firstname-lbl").hide();
            }

            if ($lastNameInput.val().trim().length < 1 || $lastNameInput.val().trim().length > 30) {
                flag = false;
                $("#error-change-lastname-lbl").show();
            } else {
                $("#error-change-lastname-lbl").hide();
            }

            if (!flag) {
                return;
            }

            const newAccountData = {
                employerDetails: {
                    firstName: $firstNameInput.val().trim(),
                    lastName: $lastNameInput.val().trim(),
                    phone: $phoneInput?.val().trim(),
                    address: $addressInput?.val().trim(),
                    about: $aboutInput?.val().trim(),
                },
                department: {
                    idDepartment: $departmentSelect.val()
                },
                position: {
                    idPosition: $positionSelect.val()
                },
                role: {
                    idRole: $roleSelect.val()
                },
            }

            $.ajax({
                method: "patch",
                url: "/accounts/" + idAccount,
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify(newAccountData),
                success: (data) => {
                    updateAccountCache(data)
                    $("#changeAccountModal").modal('hide')
                    callMessagePopup("Успех", "Данные пользователя успешно изменены. " +
                        "Если Вы изменяли роль, то она вступит в силу при следующей авторизации.")
                    fillStaffTable()
                },
                error: () => {
                    $("#changeAccountModal").modal('hide')
                    callMessagePopup("Ошибка", "Не удалось обновить пользователя")
                }
            })
        })
    }

    function callConfirmDeleteAccount(idAccountToDelete) {
        const currentAccount = getAccountCache();

        if (currentAccount.idAccount === idAccountToDelete) {
            callMessagePopup("Ошибка", "Вы не можете удалить самого себя!")
            return;
        }

        callMessagePopup("Подтвердите действие", "Вы действительно хотите удалить аккаунт?")
        $("#confirm-message-btn").on('click', () => {
            deleteAccount(idAccountToDelete)
        })
    }

    function deleteAccount(idAccount) {

        $.ajax({
            method: "delete",
            url: "/accounts/" + idAccount,
            async: false,
            success: () => {
                callMessagePopup("Успех", "Аккаунт успешно удален")
                deleteFromStaffCache(idAccount);
                fillStaffTable();
            },
            error: () => {
                callMessagePopup("Ошибка", "Ошибка удаления аккаунта")
            }
        })
    }

    function bindStaffButtons() {
        $("#create-account").on('click', () => {
            $("#accountModal").modal('show');

            fillAccountPopupSelectors(true)

            $("#confirm-account-btn").unbind();
            $("#confirm-account-btn").on("click", () => {
                const $firstName = $("#firstName-input-acc-popup");
                const $lastName = $("#lastName-input-acc-popup");
                const $phone = $("#phone-input-acc-popup");
                const $address = $("#address-input-acc-popup");
                const $about = $("#about-input-acc-popup")
                const $department = $("#department-select-acc-popup");
                const $position = $("#position-select-acc-popup");
                const $role = $("#role-select-acc-popup")
                const $username = $("#username-input-acc-popup")
                const $password = $("#password-input-acc-popup")

                let flag = true;

                if ($firstName.val().trim().length < 1 || $firstName.val().trim().length > 30) {
                    flag = false;
                    $("#error-firstname-lbl").show();
                } else {
                    $("#error-firstname-lbl").hide();
                }

                if ($lastName.val().trim().length < 1 || $lastName.val().trim().length > 30) {
                    flag = false;
                    $("#error-lastname-lbl").show();
                } else {
                    $("#error-lastname-lbl").hide();
                }

                if ($username.val().trim().length < 5 || $username.val().trim().length > 30) {
                    flag = false;
                    $("#error-username-lbl").show();
                } else {
                    $("#error-username-lbl").hide();
                }

                if ($password.val().trim().length < 5 || $password.val().trim().length > 30) {
                    flag = false;
                    $("#error-password-lbl").show();
                } else {
                    $("#error-password-lbl").hide();
                }

                if (!flag) {
                    return;
                }

                const newAccountData = {
                    employerDetails: {
                        firstName: $firstName.val().trim(),
                        lastName: $lastName.val().trim(),
                        phone: $phone?.val().trim(),
                        address: $address?.val().trim(),
                        about: $about?.val().trim(),
                    },
                    department: {
                        idDepartment: $department.val()
                    },
                    position: {
                        idPosition: $position.val()
                    },
                    role: {
                        idRole: $role.val()
                    },
                    username: $username.val(),
                    password: $password.val(),
                }

                $.ajax({
                    method: "post",
                    url: "/accounts",
                    contentType: "application/json",
                    dataType: "json",
                    data: JSON.stringify(newAccountData),
                    success: (data) => {
                        staffCache.push(data)
                        $("#accountModal").modal('hide')
                        fillStaffTable()
                    },
                    error: () => {
                        callMessagePopup("Ошибка", "Не удалось создать пользователя")
                    }
                })
            })
        })
    }

    function fillAccountPopupSelectors(isCreate) {
        loadDepartments();
        loadPositions();
        const $departmentSelect = $("#department-select-acc-popup");
        const $positionSelect = $("#position-select-acc-popup");
        const $roleSelect = $("#role-select-acc-popup");

        const $departmentChangeSelect = $("#department-change-select-popup");
        const $positionChangeSelect = $("#position-change-select-popup");
        const $roleChangeSelect = $("#role-change-select-popup");

        $positionSelect.html('')
        $departmentSelect.html('');
        $roleSelect.html('');
        $departmentChangeSelect.html('');
        $positionChangeSelect.html('');
        $roleChangeSelect.html('');

        const departmentCache = getDepartmentsCache();
        departmentCache.forEach(department => {
            const option = $("<option value='" + department.idDepartment + "'>" + department.name + "</option>");
            isCreate ? $departmentSelect.append(option) : $departmentChangeSelect.append(option)
        })

        const positionsCache = getPositionsCache();
        positionsCache.forEach(position => {
            const option = $("<option value='" + position.idPosition + "'>" + position.name + "</option>");
            isCreate ? $positionSelect.append(option) : $positionChangeSelect.append(option);
        })

        const roleCache = getRoleCache();
        roleCache.forEach(role => {
            const option = $("<option value='" + role.idRole + "'>" + role.name + "</option>");
            isCreate ? $roleSelect.append(option) : $roleChangeSelect.append(option);
        })
    }

    /////////////////////////////MY DEPARTMENT

    let myDepartmentStaffCache;

    function loadMyDepartmentStaff(idDepartment) {
        $.ajax({
            method: 'get',
            url: '/accounts?department=' + idDepartment,
            dataType: "json",
            async: false,
            success: (data) => {
                myDepartmentStaffCache = data;
                console.log(data);
            },
            error: () => {
                callMessagePopup("Ошибка", "Невозможно загрузить данные о сотрудниках")
            }
        })
    }

    function fillMyDepartmentTable() {
        const tableName = "my_department"
        const $dataTable = $("#" + tableName + "_table");

        destroyAndInitDataTable(tableName, $dataTable)

        myDepartmentStaffCache?.forEach(function (staff) {
            addRowToMyDepartmentDataTable(staff, tableName)
        })
    }

    function addRowToMyDepartmentDataTable(staff, tableName) {
        const firstName = staff.employerDetails?.firstName;
        const lastName = staff.employerDetails?.lastName;
        const positionName = staff.position?.name;
        const phone = staff.employerDetails?.phone;

        $("#" + tableName + "_table").DataTable().row.add([
            firstName ? firstName : "Не заполнено",
            lastName ? lastName : "Не заполнено",
            positionName ? positionName : "Не назначен",
            phone ? phone : "Неизвестен",
        ]).draw();
    }

    ////////////////TIMESHEET STAFF TABLE

    function fillStaffTimesheetTable() {
        const tableName = "staff_timesheet"
        const $dataTable = $("#" + tableName + "_table");

        destroyAndInitDataTable(tableName, $dataTable)

        staffCache?.forEach(function (staff) {
            addRowToStaffTimesheetDataTable(staff, tableName)
        })
    }

    function addRowToStaffTimesheetDataTable(staff, tableName) {
        const firstName = staff.employerDetails?.firstName;
        const lastName = staff.employerDetails?.lastName;
        const totalName = (firstName + " " + lastName).trim() ? (firstName + " " + lastName).trim() : "Не заполнено"
        const positionName = staff.position?.name;

        $("#" + tableName + "_table").DataTable().row.add([
            "<div style='cursor: pointer' onclick='initializeCalendar(" + staff.idAccount + ")'>" + totalName + "</div>",
            positionName ? positionName : "Не назначен",
        ]).draw();
    }
}