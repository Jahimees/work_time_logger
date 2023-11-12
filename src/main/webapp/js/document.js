{
    let documentTypesCache;
    let documentsCache;
    let authorizedIdAccount

    function initDocumentTab(idAccount) {
        authorizedIdAccount = idAccount
        loadDocumentTypes()
        initPopup()
        loadDocuments();
    }

    function patchDocumentInCache(document) {
        documentsCache.forEach(doc => {
            if (doc.idDocument === document.idDocument) {
                doc.confirmed = document.confirmed;
                doc.accountConfirmator = document.accountConfirmator;
                doc.confirmDate = document.confirmDate
            }
        })
    }

//
    function loadDocumentsForAccount(idAccount) {
        let returnDocuments
        $.ajax({
            method: "get",
            url: "/accounts/" + idAccount + "/documents",
            contentType: "application/json",
            dataType: "json",
            async: false,
            success: (data) => {
                returnDocuments = data;
                documentsCache = data;
            },
            error: () => {
                callMessagePopup("Ошибка", "Невозможно загрузить данные")
            }
        })

        return returnDocuments;
    }

    function loadDocuments() {
        $.ajax({
            method: "get",
            url: "/documents",
            contentType: "application/json",
            dataType: "json",
            async: false,
            success: (data) => {
                documentsCache = data;
            },
            error: () => {
                callMessagePopup("Ошибка", "Невозможно загрузить данные")
            }
        })
    }

    function loadDocumentTypes() {
        if (typeof documentTypesCache !== "undefined") {
            return documentTypesCache;
        }
        console.log(documentTypesCache)


        $.ajax({
            method: "get",
            url: "/document-types",
            contentType: "application/json",
            async: false,
            success: (data) => {
                console.log(data)
                documentTypesCache = data;
            }
        })
        console.log("НУ ЗАГРУЗИ")
        console.log(documentTypesCache)
        return documentTypesCache;
    }

    function initPopup() {
        $("#create-document-btn").on('click', () => {
            $("#documentModal").modal('show')

            const $documentTypeSelect = $("#document-type-select");
            $documentTypeSelect.html('');

            documentTypesCache.forEach(documentType => {
                $documentTypeSelect.append($("<option value='" + documentType.idDocumentType + "'>" + documentType.name +
                    "</option>"))
            })

            console.log("init")
            $("#confirm-document-btn").unbind();
            $("#confirm-document-btn").on("click", () => {
                $("#loading-icon").show();
                $("#documentModal").modal('hide')

                const documentName = $("#document-name-input").val();
                const dateStart = $("#date-start-input").val();
                const dateEnd = $("#date-end-input").val();

                let flag = true;

                if (typeof documentName === "undefined" ||
                    documentName.length < 1 || documentName.length > 20) {
                    $("#doc-name-invalid").show();
                    flag = false;
                } else {
                    $("#doc-name-invalid").hide();
                }

                if (dateStart === '') {
                    $("#date-start-invalid").show();
                    flag = false
                } else {
                    $("#date-start-invalid").hide();
                }

                if (dateEnd === '') {
                    $("#date-end-invalid").show();
                    flag = false
                } else {
                    $("#date-end-invalid").hide();
                }

                const startDate = new Date(dateStart);
                const endDate = new Date(dateEnd);

                if (startDate.getTime() > endDate.getTime()) {
                    $("#date-end-invalid").show();
                    $("#date-start-invalid").show();
                    flag = false;
                }

                if (!flag) return;

                const documentType = $("#document-type-select").val();

                const newDocObj = {
                    accountOwner: {
                        idAccount: authorizedIdAccount
                    },
                    sinceDate: new Date(startDate),
                    toDate: new Date(endDate),
                    text: documentName,
                    documentType: {
                        idDocumentType: documentType
                    }
                }

                let document;
                $.ajax({
                    method: "post",
                    url: "/accounts/" + authorizedIdAccount + "/documents",
                    dataType: "json",
                    contentType: "application/json",
                    async: false,
                    data: JSON.stringify(newDocObj),
                    success: (data) => {
                        document = data;
                    },
                    error: () => {
                        callMessagePopup("Ошибка", "Не удалось создать документ")
                        $("#loading-icon").hide();
                    }
                })

                const formData = new FormData();
                const $documentInputFile = $("#document-input-file");


                if ($documentInputFile[0].files.length !== 0) {
                    formData.append("file", $documentInputFile[0].files[0]);
                    formData.append("fileName", $documentInputFile[0].files[0].name + ".jpeg");
                    if (window.FormData === undefined) {
                        alert('В вашем браузере FormData не поддерживается')
                    }

                    $.ajax({
                        method: "post",
                        url: "/accounts/" + authorizedIdAccount + "/user_files?idDocument=" + document.idDocument,
                        contentType: false,
                        processData: false,
                        async: false,
                        data: formData,
                        success: (data) => {
                            document = data;
                        },
                        error: () => {
                            callMessagePopup("Ошибка", "Не удалось сохранить файл")
                            $("#loading-icon").hide();
                        }
                    })
                }

                documentsCache.push(document);
                fillDocumentTable();
                $("#loading-icon").hide();
            })
        })
    }

    function fillDocumentTable() {
        const tableName = "document"
        const $dataTable = $("#" + tableName + "_table");

        destroyAndInitDataTable(tableName, $dataTable)

        documentsCache?.forEach((document) => {
            addRowToDocumentDataTable(document, tableName)
        })
    }

    function addRowToDocumentDataTable(document, tableName) {
        const docName = document.text;
        const accOwner = document.accountOwner;
        const totalNameOwner = accOwner.employerDetails.firstName + " " + accOwner.employerDetails.lastName;
        const creationDate = new Date(document.creationDate).toLocaleDateString('ru');
        const sinceDate = new Date(document.sinceDate).toLocaleDateString('ru');
        const toDate = new Date(document.toDate).toLocaleDateString('ru');
        const confirmed = document.confirmed;

        $("#" + tableName + "_table").DataTable().row.add([
            '<div style="cursor: pointer" ' +
            'onclick="openDocDetails(' + document.idDocument + ')">' + (docName ? docName : "Не заполнено") + '</div>',
            totalNameOwner ? totalNameOwner : "Не заполнено",
            creationDate ? creationDate : "Не указана",
            sinceDate ? sinceDate : "Не указана",
            toDate ? toDate : "Не указана",
            confirmed ? "Да" : "Нет",
        ]).draw();
    }

    function openDocDetails(idDocument) {
        let document;
        documentsCache.forEach(doc => {
            if (doc.idDocument === idDocument) {
                document = doc;
            }
        })

        const $documentNamePh = $("#document-name-placeholder");
        const $documentOwnerPh = $("#document-owner-placeholder");
        const $dateCreationPh = $("#date-creation-placeholder");
        const $dateStartPh = $("#date-start-placeholder");
        const $dateEndPh = $("#date-end-placeholder");
        const $docTypePh = $("#document-type-placeholder");
        const $dateConfirmPh = $("#date-confirm-placeholder");
        const $confirmatorPh = $("#confirmator-placeholder");
        const $confirmCheckBox = $("#confirm-checkbox-placeholder");
        const $documentPhotoPh = $("#document-photo-placeholder");

        $documentNamePh.val(document.text);
        $documentOwnerPh.val(document.accountOwner.employerDetails.firstName + " " + document.accountOwner.employerDetails.lastName)
        $dateCreationPh.val(new Date(document.creationDate).toLocaleDateString('ru'));
        $dateStartPh.val(new Date(document.sinceDate).toLocaleDateString('ru'));
        $dateEndPh.val(new Date(document.toDate).toLocaleDateString('ru'))
        $docTypePh.val(document.documentType.name);
        $dateConfirmPh.val(document.confirmDate ? new Date(document.confirmDate).toLocaleString('ru') : "Не подтверждён")
        $confirmatorPh.val(document.accountConfirmator ?
            (document.accountConfirmator.employerDetails.firstName + " " + document.accountConfirmator.employerDetails.lastName)
            : "Не подтверждён")
        $confirmCheckBox[0].checked = document.confirmed;
        let src = document.file ? "../user/" + document.accountOwner.idAccount + "/docs/" + document.file : "../image/load.gif"
        $documentPhotoPh.attr('src', src);

        const currentAccount = getAccountCache();
        if (currentAccount.role.name === "ROLE_EMPLOYEER") {
            $("#confirm-view-document-btn").text("Ок");
            $("#confirm-checkbox-placeholder").attr("readonly", true);
        } else {
            $("#confirm-view-document-btn").text("Сохранить");
        }

        $("#confirm-view-document-btn").unbind();
        $("#confirm-view-document-btn").on('click', () => {
            if (currentAccount.role.name === "ROLE_EMPLOYEER") {
                $("#confirm-view-document-btn").modal('hide');
            } else {
                if (currentAccount.role.name === "ROLE_HR") {
                    patchDocument(currentAccount, document);
                } else {
                    if (document.accountOwner.department != currentAccount.department) {
                        $("#confirm-view-document-btn").modal('hide');
                        callMessagePopup("Ошибка", "Вы не можете подтверждать документы сотрудников другого отдела")
                    } else {
                        patchDocument(currentAccount, document);
                    }
                }
            }
        })

        $("#viewDocumentModal").modal('show');
    }

    function patchDocument(currentAccount, document) {
        const $confirmCheckBox = $("#confirm-checkbox-placeholder");

        const documentToPatch = {
            confirmed: $confirmCheckBox[0].checked,
            accountConfirmator: currentAccount
        }

        $.ajax({
            method: "patch",
            url: "/documents/" + document.idDocument,
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(documentToPatch),
            async: false,
            success: (data) => {
                $("#viewDocumentModal").modal('hide');
                callMessagePopup("Успех", "Данные успешно сохранены");
                patchDocumentInCache(data);
                fillDocumentTable();
            },
            error: () => {
                $("#viewDocumentModal").modal('hide');
                callMessagePopup("Ошибка", "Не удалось сохранить данные документа")
            }
        })
    }
}