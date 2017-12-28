var array = [];

function terimaDataInsertKeArray(data){ //terima data
    var subhasil = data.substring(1, data.length-1); //potong data ke 1 : [ dan data terakhir : ]
    var hasil = subhasil.split(", "); //hasil subhasil di potong setiap ada ", "
    for(i=0; i<hasil.length; i++){
        array.push(hasil[i]);
    }
}

function chartOnLoad() {
    var chart = new CanvasJS.Chart("chartContainer",
    {
      title:{
      text: "Pendapatan - per bulan"
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

        dataPoints: [
        { x: new Date(2017, 00, 1), y: parseFloat(array[0]) },
        { x: new Date(2017, 01, 1), y: parseFloat(array[1]) },
        { x: new Date(2017, 02, 1), y: parseFloat(array[2]), indexLabel: "highest",markerColor: "red", markerType: "triangle"},
        { x: new Date(2017, 03, 1), y: parseFloat(array[3]) },
        { x: new Date(2017, 04, 1), y: parseFloat(array[4]) },
        { x: new Date(2017, 05, 1), y: parseFloat(array[5]) },
        { x: new Date(2017, 06, 1), y: parseFloat(array[6]) },
        { x: new Date(2017, 07, 1), y: parseFloat(array[7]) },
        { x: new Date(2017, 08, 1), y: parseFloat(array[8]) , indexLabel: "lowest",markerColor: "DarkSlateGrey", markerType: "cross"},
        { x: new Date(2017, 09, 1), y: parseFloat(array[9]) },
        { x: new Date(2017, 10, 1), y: parseFloat(array[10]) },
        { x: new Date(2017, 11, 1), y: parseFloat(array[11]) }
        ]
      }
      ]
    });

    chart.render();
  }

