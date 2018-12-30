function checkIsRemaining(isRemaining, id){

   if(isRemaining){
    return (
      '<div><a href="/answer.html?id='+id+'" > और पढो</a></div>'
      );
   }
   else return '';
}
 function timeConverter(UNIX_timestamp){
      var a = new Date(UNIX_timestamp);
      var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
      var year = a.getFullYear();
      var month = months[a.getMonth()];
      var date = a.getDate();
      var hour = a.getHours();
      var min = a.getMinutes();
      var time = date + ' ' + month + ' ' + hour + ':' + min;
  return time;
}

function checkCanAnswerTheQuestion(canAnswerQuestion, id){
  if(canAnswerQuestion){
    return ('<div class="row content-section"><div><a href="/answer.html?id='+id+'" >इस प्रश्न का उत्तर दो</a></div></div>');
  }
  else return '';
}
function buildBox(question, usrImage, usrName, time, answer, isRemaining, id, canAnswerQuestion){

  return (
  '<div class="box"><div class="container" id="box-'+id+'" ><div class="row"><h4>'+
  question+
  '</h4></div><div class="row"><div class="col-lg-1 col-md-1 col-sm-2 col-xs-2"><img src="'+
  usrImage+
  '" class="img-circle" alt="Cinque Terre" width="30" height="30"></div><div class="col-lg-3 col-md-3 col-sm-10 col-xs-10"><div>'+
    usrName+
                    '</div><div class="time-bar">'+
                      timeConverter(time)+
                    '</div> </div> </div><div class="row content-section">'+
                answer
                +
                checkIsRemaining(isRemaining, id)
                +
              '</div><div>'+
              checkCanAnswerTheQuestion(canAnswerQuestion, id)
              +'</div></div></div>'
    );
}

