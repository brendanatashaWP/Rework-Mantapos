//tableCart dideklarasikan di dalam function document ready karena supaya deklarasi element-nya saat document sudah ready,
//jadi gak null
$(document).ready(function() {
    // put your page initialization code here
    var tableCart = document.getElementById("tableCart");
    var textTotalBayar = document.getElementById("totalBayar");
    var inputPriceTotal = document.getElementById("inputPriceTotal");
});

//Deklarasi variabel ini gak perlu menunggu document ready
var priceTotal = 0;
var counterOrder = 0;
var kembalian = 0;

function addToCart(id, name, price){
    //variabel2 untuk cell dari table order nya..
    var row = tableCart.insertRow(-1);
    var cellName = row.insertCell(0);
    var cellQty = row.insertCell(1);
    var cellPrice = row.insertCell(2);
    var cellPriceTotal = row.insertCell(3);
    var cellRemove = row.insertCell(4);
    var cellIdHidden = row.insertCell(5);

    cellIdHidden.style.display = "none";
    cellIdHidden.innerHTML = id;

    cellName.innerHTML = name;
    cellQty.innerHTML = '<input type="number" value="1" min="1" onchange="updatePrice(this)"/>';
    cellPrice.innerHTML = price;
    cellPriceTotal.innerHTML = price;

    cellRemove.innerHTML = '<a href="#" onclick="deleteItem(this)" style="text-decoration: none; color: red;">REMOVE</a>';

    //Jika ada order baru, counter++
    counterOrder++;
    //set variabel priceTotal menjadi 0 lagi.
    priceTotal = 0;

    //Iterasi sebanyak jumlah counterOrder
    for(i=0; i<counterOrder; i++){
        //nilai priceTotal new adalah priceTotal old ditambahkan dengan hasil kali antara nilai price dengan quantity per rows
        priceTotal+=tableCart.rows[i+1].cells[2].innerHTML * tableCart.rows[i+1].cells[1].children[0].value;
    }

    //set nilai totalBayar
    totalBayar.innerHTML = "Rp " + priceTotal;
    inputPriceTotal.value = priceTotal;
}

function updatePrice(x){
    //variabel baris adalah baris ke-x dari baris tabel yang kita ubah-ubah
    var baris = x.parentNode.parentNode;

    //variabel utk menyimpan nilai dari input quantity
    var col_qty = tableCart.rows[baris.rowIndex].cells[1].children[0].value;
    //variabel utk menyimpan nilai dari item price
    var col_price = tableCart.rows[baris.rowIndex].cells[2].innerHTML;
    //variabel utk menyimpan nilai dari price total
    var col_price_total = tableCart.rows[baris.rowIndex].cells[3].innerHTML;

    //Ketika input quantity berubah,
    //nilai variabel priceTotal dikurangi dengan nilai dari priceTotal yang ada di cell yg bersesuaian
    priceTotal-=col_price_total;
    //nilai variabel priceTotal yang baru adalah nilai priceTotal yang old + nilai cell item price * quantity
    priceTotal += col_price*col_qty;

    //Mengganti nilai dari cell ke-3 tabel (yaitu cell Total Price) dengan nilai cell item price * quantity
    tableCart.rows[baris.rowIndex].cells[3].innerHTML = col_price*col_qty;

    //Update text totalBayar
    totalBayar.innerHTML = "Rp " + priceTotal;
    inputPriceTotal.value = priceTotal;
}

function deleteItem(x) {
    //ambil baris dari item yang sesuai
    var baris = x.parentNode.parentNode;
    //Mengurangi priceTotal dengan total price dari baris yang sesuai
    priceTotal-=tableCart.rows[baris.rowIndex].cells[3].innerHTML;
    //Mengurangi counterOrder, supaya saat ada order baru, counterOrder jumlahnya berkurang 1 setelah di delete ini
    counterOrder--;
    baris.parentNode.removeChild(baris);
    totalBayar.innerHTML = "Rp " + priceTotal;
}

function addOrder() {
    var CashModal = document.getElementById("cash");
    kembalian = CashModal.value - priceTotal;
    var CustNameModal = document.getElementById("custName");
    var inputCustName = document.getElementById("inputCustName");
    inputCustName.value = CustNameModal.value;
    var TableNoModal = document.getElementById("tableNo");
    var inputTableNo = document.getElementById("inputTableNo");
    inputTableNo.value = TableNoModal.value;
    var txtKembalian = document.getElementById("kembalian");
    txtKembalian.innerHTML = "Rp " + kembalian;
    var inputArrayIdOrder = document.getElementById("array_id_order");

    var array_id_order = [];
    for (i=0; i<counterOrder; i++){
        array_id_order.push(tableCart.rows[i+1].cells[5].innerHTML);
    }
    inputArrayIdOrder.value = array_id_order;
}

function setNotes() {
    var notesModal = document.getElementById("notes");
    var inputNotes = document.getElementById("inputNotesChange");
    inputNotes.value = notesModal.value;
}

function passToChange() {
    //Passing inputan dari input di modal paymentModal ke input di changeModal (untuk nantinya di
    //insert ke database
    var CustNameModal = document.getElementById("custName"); //customer name
    var inputCustNameChange = document.getElementById("inputCustNameChange");
    inputCustNameChange.value = CustNameModal.value;
    var TableNoModal = document.getElementById("tableNo");
    var inputTableNoChange = document.getElementById("inputTableNoChange"); //table no
    inputTableNoChange.value = TableNoModal.value;
    var CashModal = document.getElementById("cash"); //calculate kembalian
    kembalian = CashModal.value - priceTotal;
    var txtKembalian = document.getElementById("kembalian");
    txtKembalian.innerHTML = "Rp " + kembalian; //set text kembalian
    var inputArrayIdOrder = document.getElementById("array_id_orderChange");
    var inputPriceTotal = document.getElementById("inputPriceTotal");
    var inputPriceTotalChange = document.getElementById("inputPriceTotalChange");
    inputPriceTotalChange.value = inputPriceTotal.value;

    var array_qtyChange = document.getElementById("array_qtyChange");

    var array_id_order = [];
    for (i=0; i<counterOrder; i++){
        array_id_order.push(tableCart.rows[i+1].cells[5].innerHTML);
    }
    inputArrayIdOrder.value = array_id_order;

    var array_qty = [];
    for(i=0;i<counterOrder; i++){
        array_qty.push(tableCart.rows[i+1].cells[1].children[0].value);
    }

    array_qtyChange.value = array_qty;
}