// Функция обработки формы авторизации
function sendFormData() {

    // Получение данных формы
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    // Формирование HTTP запроса
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "loginServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    // Обработка ответа сервера
    xhr.onload = function () {
        let response;

        // Ответ успешно получен
        if (xhr.status === 200) {
            response = Number(xhr.responseText);
            switch (response) {
                case 1:
                    // Вывод сообщения об ошибке
                    alert("Invalid username or password");
                    break;
                case 0:
                    // Перенаправление в линый кабинет пользователя
                    location.replace(location.host + "/groshi/dreams.jsp");
            }
        }
        // Вывод сообщения об ошибке
        else {
            alert("Error: " + xhr.status);
            alert("Location: " + location.host + "/groshi/dreams.jsp");
        }
    };

    // Подготовка данных для запроса
    var data =
        "username=" + encodeURIComponent(username) +
        "&password=" + encodeURIComponent(password);

    // Отправка запроса сервлету
    xhr.send(data);
}
