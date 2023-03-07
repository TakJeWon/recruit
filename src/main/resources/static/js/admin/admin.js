
let board_title = document.getElementById('board_title');
let board_start_date = document.getElementById('startDatePicker');
let board_end_date = document.getElementById('endDatePicker');
let board_experience = document.getElementById('board_experience');
let board_education = document.getElementById('board_education');
let board_type = document.getElementById('board_type');
let board_location = document.getElementById('board_location');
let board_content = tinymce.get('content')

function admin_board_submit(){
    if (board_title.value === ""){
        Swal.fire("Error", "제목을 입력해주세요", "error");
        board_title.focus();
        return;
    }

    if (board_start_date.value == ""){
        Swal.fire("Error", "시작 날짜를 입력해주세요", "error");
        board_start_date.focus();
        return;
    }

    if (board_end_date.value == ""){
        Swal.fire("Error", "마감 날짜를 입력해주세요", "error");
        board_end_date.focus();
        return;
    }

    if (board_experience.value == ""){
        Swal.fire("Error", "경력을 입력해주세요", "error");
        board_experience.focus();
        return;
    }
    
    if (board_education.value == ""){
        Swal.fire("Error", "근무 형태를 입력해주세요", "error");
        board_type.focus();
        return;
    }
    
    if (board_location.value == ""){
        Swal.fire("Error", "근무지를 입력해주세요", "error");
        board_location.focus();
        return;
    }

    if (board_type.value == ""){
        Swal.fire("Error", "근무 형태를 입력해주세요", "error");
        board_type.focus();
        return;
    }

    // tinymce.activeEditor.setProgressState(true);
    // tinymce.triggerSave();
    // if (board_content.value == ""){
    //     Swal.fire("Error", "공고 내용을 입력해주세요", "error");
    //     board_content.focus();
    //     return;
    // }

    var board_file = $('#board_file')[0];

    var formData = new FormData();
    formData.append('title', board_title.value);
    formData.append('start_date', board_start_date.value);
    formData.append('end_date', board_end_date.value);
    formData.append('experience', board_experience.value);
    formData.append('education', board_education.value);
    formData.append('type', board_type.value);
    formData.append('location', board_location.value);
    formData.append('content', "board_content.value");
    formData.append('file', board_file);

    Swal.fire({
        title: '공고 등록',
        text: '채용 공고를 등록하시겠습니까?',
        icon: 'Question',

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '등록', // confirm 버튼 텍스트 지정
        cancelButtonText: '취소', // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
    }).then(result => {
        if (result.isConfirmed){
            $.ajax({
                type: "POST",
                url: "/admin/register/success",
                processData: false,
                contentType: false,
                data: formData,
                success: function(data) {
                    Swal.fire("성공", "등록을 정상적으로 완료하였습니다.", "success");
                },
                error : function(e) {
                    Swal.fire("실패", "작업수행에 실패하였습니다. 관리자에게 문의해주세요.", "error");
                },
                timeout:100000
            })
        }
    });

}




let email_address = document.getElementById('admin_email');
let password = document.getElementById('admin_password');

function admin_member_submit(){
    console.log("admin_member_submit!", email_address.value)
    if (email_address.value == "") {
        Swal.fire("Error", "이메일 주소를 입력해주세요", "error");
        email_address.focus();
        return;
    }

    if (password.value == ""){
        Swal.fire("Error", "비밀번호를 입력해주세요", "error");
        return;
    }

    Swal.fire({
        title: '계정 등록',
        text: 'Admin 계정을 등록하시겠습니까?',
        icon: 'Question',

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '등록', // confirm 버튼 텍스트 지정
        cancelButtonText: '취소', // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
    }).then(result => {
        if (result.isConfirmed){
            $.ajax({
                type: "POST",
                url: "/admin/member/success",
                data: {
                    "email" : email_address.value,
                    "password" : password.value,
                },
                success: function(data) {
                    Swal.fire("성공", "등록을 정상적으로 완료하였습니다.", "success");
                },
                error : function(e) {
                    Swal.fire("실패", "작업수행에 실패하였습니다. 관리자에게 문의해주세요.", "error");
                },
                timeout:100000
            })
        }
    });


}