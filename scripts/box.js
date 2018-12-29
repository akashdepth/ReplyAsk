function checkIsRemaining(isRemaining, url){

   if(isRemaining){
    return (
      '<div><a href="/'+url+'" > Read More</a></div>'
      );
   }
   else return '';
}

function checkCanAnswerTheQuestion(canAnswerQuestion, url){
  if(canAnswerQuestion){
    return ('<div class="row content-section"><div><a href="'+url+'" >Answer the question</a></div></div>');

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
                      time+
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

