let editor;

document.addEventListener('DOMContentLoaded', function() {
    initializeEditor()
})

function initializeEditor() {

    if (editor) {
        editor.destroy()
    }

    editor = ExploreEditor.create(document.getElementById('kothing-editor'), {
        width: '100%',
        minHeight: 300,
        toolbarItem: [
            ['undo', 'redo'],
            ['fontSize', 'formatBlock'],
            ['bold', 'underline', 'italic', 'strike', 'fontColor', 'hiliteColor'],
            '/', // Line break
            ['outdent', 'indent', 'align', 'list', 'horizontalRule'],
            ['link', 'table', 'image'],
            ['preview', 'print'],
            ['removeFormat']
        ],
    })

}


function delete_board(boardId){

    Swal.fire({
        title: '공고 삭제',
        text: '해당 공고를 삭제하시겠습니까?',
        icon: 'Question',

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
        cancelButtonText: '취소', // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
    }).then(result => {
        if (result.isConfirmed){
            $.ajax({
                type: "GET",
                url: "/admin/register/delete?id=" + boardId,
                success: function() {
                    Swal.fire({
                        icon: 'Success',
                        title: '삭제 성공',
                        text: '선택하신 공고를 삭제하였습니다!'
                    }).then(function (){
                        location.reload()
                    })
                },
                error : function(e) {
                    Swal.fire("실패", "작업수행에 실패하였습니다. 관리자에게 문의해주세요.", "error");
                },
                timeout:100000
            })
        }
    });

}


let board_title = document.getElementById('board_title');
let board_start_date = document.getElementById('startDatePicker');
let board_end_date = document.getElementById('endDatePicker');
let board_experience = document.getElementById('board_experience');
let board_education = document.getElementById('board_education');
let board_type = document.getElementById('board_type');
let board_location = document.getElementById('board_location');

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
        Swal.fire("Error", "학력을 입력해주세요", "error");
        board_type.focus();
        return;
    }

    if (board_type.value == ""){
        Swal.fire("Error", "근무 형태를 입력해주세요", "error");
        board_type.focus();
        return;
    }
    
    if (board_location.value == ""){
        Swal.fire("Error", "근무지를 입력해주세요", "error");
        board_location.focus();
        return;
    }

    let board_content = editor.getContents();

    if (!board_content || board_content === "<p><br></p>"){
        Swal.fire("Error", "공고 내용을 입력해주세요", "error");
        board_content.focus();
        return;
    }

    // tinymce.activeEditor.setProgressState(true);
    // tinymce.triggerSave();
    // if (board_content.value == ""){
    //     Swal.fire("Error", "공고 내용을 입력해주세요", "error");
    //     board_content.focus();
    //     return;
    // }

    var board_file = $('#board_file')[0].files[0];

    var formData = new FormData();
    formData.append('title', board_title.value);
    formData.append('start_date', board_start_date.value);
    formData.append('end_date', board_end_date.value);
    formData.append('experience', board_experience.value);
    formData.append('education', board_education.value);
    formData.append('type', board_type.value);
    formData.append('location', board_location.value);
    formData.append('content', board_content);
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
                    Swal.fire({
                        icon: 'Success',
                        title: '등록 성공',
                        text: '공고 등록에 성공하였습니다!'
                    }).then(function (){
                    location.reload()
                    })
                },
                error : function(e) {
                    Swal.fire("실패", "작업수행에 실패하였습니다. 관리자에게 문의해주세요.", "error");
                },
                timeout:100000
            })
        }
    });

}

function admin_board_modify(boardId){

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
        Swal.fire("Error", "학력을 입력해주세요", "error");
        board_type.focus();
        return;
    }

    if (board_type.value == ""){
        Swal.fire("Error", "근무 형태를 입력해주세요", "error");
        board_type.focus();
        return;
    }

    if (board_location.value == ""){
        Swal.fire("Error", "근무지를 입력해주세요", "error");
        board_location.focus();
        return;
    }

    let board_content = editor.getContents();

    if (!board_content || board_content === "<p><br></p>"){
        Swal.fire("Error", "공고 내용을 입력해주세요", "error");
        board_content.focus();
        return;
    }

    // tinymce.activeEditor.setProgressState(true);
    // tinymce.triggerSave();
    // if (board_content.value == ""){
    //     Swal.fire("Error", "공고 내용을 입력해주세요", "error");
    //     board_content.focus();
    //     return;
    // }


    var formData = new FormData();
    formData.append('title', board_title.value);
    formData.append('start_date', board_start_date.value);
    formData.append('end_date', board_end_date.value);
    formData.append('experience', board_experience.value);
    formData.append('education', board_education.value);
    formData.append('type', board_type.value);
    formData.append('location', board_location.value);
    formData.append('content', board_content);

    if($('#board_file')[0]){
        var board_file = $('#board_file')[0].files[0];
        formData.append('file', board_file)
    } else {
        let file_exist = document.getElementById('file_exist');
        formData.append('file', file_exist.value);
    }

    Swal.fire({
        title: '공고 수정',
        text: '채용 공고를 수정하시겠습니까?',
        icon: 'Question',

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '수정', // confirm 버튼 텍스트 지정
        cancelButtonText: '취소', // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
    }).then(result => {
        if (result.isConfirmed){
            $.ajax({
                type: "POST",
                url: "/admin/register/update/" + boardId,
                processData: false,
                contentType: false,
                data: formData,
                success: function(data) {
                    Swal.fire({
                        icon: 'Success',
                        title: '수정 성공',
                        text: '공고 수정 성공하였습니다!'
                    }).then(function (){
                        location.reload()
                    })
                },
                error : function(e) {
                    Swal.fire("실패", "작업수행에 실패하였습니다. 관리자에게 문의해주세요.", "error");
                },
                timeout:100000
            })
        }
    });

}

function admin_file_delete(boardId){

    Swal.fire({
        title: '첨부 파일 삭제',
        text: '첨부파일을 삭제하시겠습니까?',
        icon: 'Question',

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
        cancelButtonText: '취소', // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
    }).then(result => {
        if (result.isConfirmed){
            $.ajax({
                type: "GET",
                url: "/admin/file/delete?id=" + boardId,
                success: function() {
                    Swal.fire({
                        icon: 'Success',
                        title: '삭제 성공',
                        text: '첨부된 파일을 삭제하였습니다!'
                    }).then(function (){
                        location.reload()
                    })
                },
                error : function(e) {
                    Swal.fire("실패", "작업수행에 실패하였습니다. 관리자에게 문의해주세요.", "error");
                },
                timeout:100000
            })
        }
    });

}


function cancel_update_board(){

    Swal.fire({
        title: '수정 취소',
        text: '수정을 그만두고 공고 목록으로 돌아갈까요?',
        icon: 'Question',

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '목록으로', // confirm 버튼 텍스트 지정
        cancelButtonText: '취소', // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
    }).then(result => {
        if (result.isConfirmed){
            $.ajax({
                type: "GET",
                url: "/admin/cancel_board_update",
                success: function(response) {
                    $('html').css("cursor", "auto");
                    $('.wrap-loading').addClass('display-none');
                    window.location.href = response;
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


function admin_member_delete(memberId){

    Swal.fire({
        title: 'admin 계정 삭제',
        text: 'admin 계정을 삭제하시겠습니까?',
        icon: 'Question',

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
        cancelButtonText: '취소', // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
    }).then(result => {
        if (result.isConfirmed){
            $.ajax({
                type: "GET",
                url: "/admin/member/delete?id=" + memberId,
                success: function() {
                    Swal.fire({
                        icon: 'Success',
                        title: '삭제 성공',
                        text: 'admin 계정을 삭제하였습니다!'
                    }).then(function (){
                        location.reload()
                    })
                },
                error : function(e) {
                    Swal.fire("실패", "작업수행에 실패하였습니다. 관리자에게 문의해주세요.", "error");
                },
                timeout:100000
            })
        }
    });

}


function delete_apply(boardId){

    Swal.fire({
        title: '지원 내역 삭제',
        text: '선택한 지원자의 정보를 삭제하시겠습니까?',
        icon: 'Question',

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
        cancelButtonText: '취소', // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
    }).then(result => {
        if (result.isConfirmed){
            $.ajax({
                type: "GET",
                url: "/admin/apply/delete?id=" + boardId,
                success: function() {
                    Swal.fire({
                        icon: 'Success',
                        title: '삭제 성공',
                        text: '선택하신 지원자의 정보를 삭제하였습니다!'
                    }).then(function (){
                        location.reload()
                    })
                },
                error : function(e) {
                    Swal.fire("실패", "작업수행에 실패하였습니다. 관리자에게 문의해주세요.", "error");
                },
                timeout:100000
            })
        }
    });
}

let mail_title = document.getElementById('mail_title')

function apply_mail_setting(){

    if (mail_title.value == ""){
        Swal.fire("Error", "메일 제목을 입력해주세요", "error");
        mail_title.focus();
        return;
    }

    let mail_content = editor.getContents();

    if (!mail_content || mail_content === "<p><br></p>"){
        Swal.fire("Error", "메일 내용을 입력해주세요", "error");
        mail_content.focus();
        return;
    }

    Swal.fire({
        title: '메일 내용 수정',
        text: '지원자 메일 내용을 수정하시겠습니까?',
        icon: 'Question',

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '수정', // confirm 버튼 텍스트 지정
        cancelButtonText: '취소', // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
    }).then(result => {
        if (result.isConfirmed){
            $.ajax({
                type: "POST",
                url: "/admin/applyMail/modify",
                data: {
                    "title" : mail_title.value,
                    "content" : mail_content,
                },
                success: function() {
                    Swal.fire({
                        icon: 'Success',
                        title: '수정 성공',
                        text: '지원자 메일 정보를 수정하였습니다!'
                    }).then(function (){
                        location.reload()
                    })
                },
                error : function(e) {
                    Swal.fire("실패", "작업수행에 실패하였습니다. 관리자에게 문의해주세요.", "error");
                },
                timeout:100000
            })
        }
    });
}


let message_title = document.getElementById('message_title')

function apply_success_setting(){

    if (message_title.value == ""){
        Swal.fire("Error", "제목을 입력해주세요", "error");
        message_title.focus();
        return;
    }

    let message_content = editor.getContents();

    if (!message_content || message_content === "<p><br></p>"){
        Swal.fire("Error", "내용을 입력해주세요", "error");
        message_content.focus();
        return;
    }

    Swal.fire({
        title: '지원 완료 내용 수정',
        text: '지원 완료 내용을 수정하시겠습니까?',
        icon: 'Question',

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '수정', // confirm 버튼 텍스트 지정
        cancelButtonText: '취소', // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
    }).then(result => {
        if (result.isConfirmed){
            $.ajax({
                type: "POST",
                url: "/admin/successMsg/modify",
                data: {
                    "title" : message_title.value,
                    "content" : message_content,
                },
                success: function() {
                    Swal.fire({
                        icon: 'Success',
                        title: '수정 성공',
                        text: '지원 완료 내용을 수정하였습니다!'
                    }).then(function (){
                        location.reload()
                    })
                },
                error : function(e) {
                    Swal.fire("실패", "작업수행에 실패하였습니다. 관리자에게 문의해주세요.", "error");
                },
                timeout:100000
            })
        }
    });
}