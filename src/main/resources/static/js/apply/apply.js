

let jobTitle
let applyName
let birthday
let email
let editor

// document.addEventListener('DOMContentLoaded', function () {
//     jobTitle = $('#jobTitle').val()
//     applyName = $('#applyName').val()
//     birthday = $('#birthday').val()
//     email = $('#email').val()
//
//     $('#save_btn').click(function (){
//         saveArticle(4)
//     })
// })

// function showNeedPopup(errorString) {
//     Swal.fire({
//         icon: 'error',
//         title: errorString,
//         showConfirmButton: false,
//         timer: 1000
//     })
// }
//
// function saveArticle(jobId) {
//
//     let name = $('#applyName').val()
//     let birthday = $('#birthday').val()
//     let email = $('#email').val()
//
//     if (!name) {
//         showNeedPopup("이름을 작성해주세요.");
//         return
//     }
//     if (!birthday) {
//         showNeedPopup("생일을 작성해주세요.");
//         return
//     }
//     if (!email) {
//         showNeedPopup("이메일을 작성해주세요.");
//         return
//     }
//
//     $.ajax({
//         type: 'post',
//         contentType: "application/json;charset=UTF-8",
//         url: '/apply/success/' + jobId,
//         data: new FormData($("#upload-file-form")[0]),
//         success: function (data) {
//             Swal.fire({
//                 icon: 'info',
//                 title: '지원이 완료되었습니다. 작성하신 이메일로 메일을 보내드립니다.',
//                 showConfirmButton: false,
//                 timer: 1000
//             }).then(function () {
//                 location.reload()
//             })
//         },
//         error: function (data) {
//             Swal.fire("ERROR:" + data.responseText);
//         },
//     });
// }

// $(document).ready(function (){
//
//     function saveArticle(jobId) {
//         let name = $('#applyName').val()
//         let birthday = $('#birthday').val()
//         let email = $('#email').val()
//
//         if (!name) {
//             showNeedPopup("이름을 작성해주세요.");
//             return
//         }
//         if (!birthday) {
//             showNeedPopup("생일을 작성해주세요.");
//             return
//         }
//         if (!email) {
//             showNeedPopup("이메일을 작성해주세요.");
//             return
//         }
//
//         $.ajax({
//             type: 'post',
//             contentType: "application/json;charset=UTF-8",
//             url: '/apply/success/' + jobId,
//             data: new FormData($("#upload-file-form")[0]),
//             success: function (data) {
//                 Swal.fire({
//                     icon: 'info',
//                     title: '지원이 완료되었습니다. 작성하신 이메일로 메일을 보내드립니다.',
//                     showConfirmButton: false,
//                     timer: 1000
//                 }).then(function () {
//                     location.reload()
//                 })
//             },
//             error: function (data) {
//                 Swal.fire("ERROR:" + data.responseText);
//             },
//         });
//     }
// })