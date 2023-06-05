
function reloadPostTable() {
    fetch('http://localhost:8080/admin/post/').then(
        response => {
            response.json().then(
                data => {
                    let temp = "";
                    data.forEach((p) => {
                        temp += "<tr >";
                        temp += "<td >" + p.id + "</td>";
                        temp += "<td >" + p.user.name + "</td>";
                        temp += "<td >" + p.title + "</td>";
                        temp += "<td >" + p.text + "</td>";
                        temp += "<td >" + p.dateCreate + "</td>";
                        temp += "<td >" +
                            "<a class='btn btn-danger' role='button' onmouseover='fillDeletePostModal(" + p.id + ")' " +
                            "data-toggle='modal' data-target='#deletePost'>Delete</a>" +
                            "</td>"
                        temp += "</tr>";
                    })
                    $("#postTableHere").empty();
                    $("#postTableHere").append(temp);
                }
            )
        }
    )
}

function reloadProfileTable() {
    fetch('http://localhost:8080/user/post/postline/').then(
        response => {
            response.json().then(
                data => {
                    let temp = "";
                    data.forEach((p) => {
                        temp += "<tr >";
                        temp += "<td >" + p.id + "</td>";
                        temp += "<td >" + p.user.name + "</td>";
                        temp += "<td >" + p.title + "</td>";
                        temp += "<td >" + p.text + "</td>";
                        temp += "<td >" + p.dateCreate + "</td>";
                        temp += "<td >" +
                            "<a class='btn btn-info' role='button' onmouseover='fillEditPostModal(" + p.id + ")' data-toggle='modal' data-target='#editPost'>Edit</a>" +
                            "<a class='btn btn-danger' role='button' onmouseover='fillDeleteMyPostModal(" + p.id + ")' " +
                            "data-toggle='modal' data-target='#deleteMyPost'>Delete</a>" +
                            "</td>"
                        temp += "</tr>";
                    })
                    $("#profileTableHere").empty();
                    $("#profileTableHere").append(temp);
                }
            )
        }
    )
}


function fillEditPostModal(postId) {
    $.get("http://localhost:8080/user/post/postline/" + postId, function (postJSON) {
        $('#idToEditPost').val(postJSON.id);
        $('#authorToEditPost').val(postJSON.user);
        $('#titleToEditPost').val(postJSON.title);
        $('#textToEditPost').val(postJSON.text);
    });
}

function fillDeletePostModal(postId) {
    $.get("http://localhost:8080/admin/post/" + postId, function (postJSON) {
        $('#idToDeletePost').val(postJSON.id);
        $('#authorToDeletePost').val(postJSON.user.name);
        $('#titleToDeletePost').val(postJSON.title);
        $('#textToDeletePost').val(postJSON.text);
        $('#dateToDeletePost').val(postJSON.dateCreate);
    });
}

function fillDeleteMyPostModal(postId) {
    $.get("http://localhost:8080/user/post/postline/" + postId, function (postJSON) {
        $('#idToDeleteMyPost').val(postJSON.id);
        $('#authorToDeleteMyPost').val(postJSON.user.name);
        $('#titleToDeleteMyPost').val(postJSON.title);
        $('#textToDeleteMyPost').val(postJSON.text);
    });
}

function reloadNewPostTable() {
    $('#newTitle').val('');
    $('#newText').val('');
}




$(function () {
    $('#addPostSubmit').on("click", function () {
        let checked = [];
        $('input:checkbox:checked').each(function () {
            checked.push($(this).val());
        });
        let myPost = {
            text: $("#newText").val(),
            title: $("#newTitle").val()
        };
        fetch('http://localhost:8080/user/post', {
            method: "POST",
            credentials: 'same-origin',
            body: JSON.stringify(myPost),
            headers: {
                'content-type': 'application/json'
            }
        })
            .then(() => {
                reloadProfileTable();
                reloadNewPostTable();
                reloadProfileTable()
            })
    })

    $('#modalDeletePostBtn').on("click", function () {
        fetch('http://localhost:8080/admin/post/' + $('#idToDeletePost').val(), {
            method: "DELETE",
            credentials: 'same-origin',
        })
            .then(() => {
                reloadPostTable();
                // reloadProfileTable();
            })
    });

    $('#modalDeleteMyPostBtn').on("click", function () {
        fetch('http://localhost:8080/user/post/postline/' + $('#idToDeleteMyPost').val(), {
            method: "DELETE",
            credentials: 'same-origin',
        })
            .then(() => {
                reloadPostTable();
                reloadProfileTable();
            })
    });

    $('#modalEditPostBtn').on("click", function () {
        let checked = [];
        $('input:checkbox:checked').each(function () {
            checked.push($(this).val());
        });

        let post = {
            id: $('#idToEditPost').val(),
            title: $("#titleToEditPost").val(),
            text: $("#textToEditPost").val(),
        };
        fetch('http://localhost:8080/user/post/postline/', {
            method: "PUT",
            credentials: 'same-origin',
            body: JSON.stringify(post),
            headers: {
                'content-type': 'application/json'
            }
        })
            .then(() => {
                reloadProfileTable();
            })
    });
});
reloadTable();
reloadProfileTable()
reloadPostTable();