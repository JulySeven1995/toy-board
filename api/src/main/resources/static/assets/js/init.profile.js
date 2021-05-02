function getUserInfo(userId, target) {

    $.ajax(
        {
            type: "GET",
            url: "/api/user/getUserInfo",
            data: {
                "email": userId
            },
            dataType: "json",
            success: function (data) {
                console.log(data);
                target.text(data.userName);
            },
            error: function (error) {
                console.log(error);
                alert('문제가 생겼습니다! 관리자한테 연락해주세요!');
            }
        }
    )
}