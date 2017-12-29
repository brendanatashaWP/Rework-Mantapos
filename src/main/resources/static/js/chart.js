var array = [];
var idxlabel, color, marker;
var min=0, max=0;
var arrmin=0, arrmax=0;
var dataPointTest = [];

function terimaDataInsertKeArray(data){ //terima data
    var subhasil = data.substring(1, data.length-1); //ambil data index ke 1 sampai length-1
    var hasil = subhasil.split(", "); //hasil subhasil di potong setiap ada ", "
    for(i=0; i<hasil.length; i++){
        array.push(hasil[i]);
    }
    cariMinMax();
}

function cariMinMax(){

    for(var i = 0; i<12; i++){
        if(i == 0){
            min = parseFloat(array[i]);
            arrmin = i;
            max = parseFloat(array[i]);
            arrmax = i;
        }
        else if(parseFloat(array[i]) < min){
            min= parseFloat(array[i]);
            arrmin = i;
        }
        else if(parseFloat(array[i]) > max){
            max= parseFloat(array[i]);
            arrmax = i;
         }
    }

    for(i=0; i<12; i++){
        if(i==arrmax){
            dataPointTest.push({ x: new Date(2017, i+1, 1), y: parseFloat(array[i]), indexLabel: "Highest", markerColor: "green", markerType: "triangle"});
        } else if(i==arrmin){
            dataPointTest.push({ x: new Date(2017, i+1, 1), y: parseFloat(array[i]), indexLabel: "Lowest", markerColor: "red", markerType: "cross"});
        } else{
            dataPointTest.push({ x: new Date(2017, i+1, 1), y: parseFloat(array[i])});
        }
    }
}


function chartOnLoad() {
    var date = new Date();
    var currentYear = date.getFullYear();
       var chart = new CanvasJS.Chart("chartContainer",
       {

         title:{
         text: "Penjualan - per bulan"
         },
         axisX: {
           valueFormatString: "MMM",
           interval:1,
           intervalType: "month"
         },
         axisY:{
           includeZero: false

         },
         data: [
         {
           type: "line",
           dataPoints: dataPointTest

//           dataPoints: [
//           { x: new Date(2017, 00, 1), y: parseFloat(array[0]), indexLabel: idxlabel, markerColor: color, markerType: marker},
//           { x: new Date(2017, 01, 1), y: parseFloat(array[1]), indexLabel: idxlabel, markerColor: color, markerType: marker},
//             { x: new Date(2017, 02, 1), y: parseFloat(array[2]), indexLabel: idxlabel, markerColor: color, markerType: marker},
//           { x: new Date(2017, 03, 1), y: parseFloat(array[3]), indexLabel: idxlabel, markerColor: color, markerType: marker },
//           { x: new Date(2017, 04, 1), y: parseFloat(array[4]), indexLabel: idxlabel, markerColor: color, markerType: marker },
//           { x: new Date(2017, 05, 1), y: parseFloat(array[5]), indexLabel: idxlabel, markerColor: color, markerType: marker },
//           { x: new Date(2017, 06, 1), y: parseFloat(array[6]), indexLabel: idxlabel, markerColor: color, markerType: marker },
//           { x: new Date(2017, 07, 1), y: parseFloat(array[7]), indexLabel: idxlabel, markerColor: color, markerType: marker },
//           { x: new Date(2017, 08, 1), y: parseFloat(array[8]) , indexLabel: idxlabel, markerColor: color, markerType: marker},
//           { x: new Date(2017, 09, 1), y: parseFloat(array[9]), indexLabel: idxlabel, markerColor: color, markerType: marker },
//           { x: new Date(2017, 10, 1), y: parseFloat(array[10]), indexLabel: idxlabel, markerColor: color, markerType: marker },
//           { x: new Date(2017, 11, 1), y: parseFloat(array[11]), indexLabel: idxlabel, markerColor: color, markerType: marker }
//           ]
         }
         ]

       });

       chart.render();
     }