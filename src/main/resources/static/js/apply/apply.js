
let apply_name = document.getElementById('apply_name');
let apply_birthday = document.getElementById('apply_birthday');
let apply_email = document.getElementById('apply_email');

function apply_submit(jobId){

    console.log("jobID", jobId);
    if (apply_name.value == "") {
        Swal.fire("Error", "이름을 입력해주세요", "error");
        apply_name.focus();
        return;
    }

    if (apply_birthday.value == ""){
        Swal.fire("Error", "생일을 입력해주세요", "error");
        apply_birthday.focus();
        return;
    }

    if (apply_email.value == ""){
        Swal.fire("Error", "이메일을 입력해주세요", "error");
        apply_email.focus();
        return;
    }

    var form = $('#apply_file')[0].files[0];
    var formData = new FormData();

    formData.append('name', apply_name.value);
    formData.append('birthday', apply_birthday.value);
    formData.append('email', apply_email.value);
    formData.append('file', form);

    Swal.fire({
        title: '지원하기',
        text: '해당 공고에 지원 하시겠습니까?',
        icon: 'Question',

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '지원 완료', // confirm 버튼 텍스트 지정
        cancelButtonText: '취소', // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
    }).then(result => {
        if (result.isConfirmed){
            $.ajax({
                type: "POST",
                url: "/apply/success/" + jobId,
                data: formData,
                contentType: false,
                processData: false,
                success: function(data) {
                    Swal.fire("성공", "등록을 정상적으로 완료하였습니다.", "success");
                },
                error : function(e) {
                    Swal.fire("실패", "지원에 실패하였습니다. 관리자에게 문의해주세요.", "error");
                },
                timeout:100000
            })
        }
    });


}