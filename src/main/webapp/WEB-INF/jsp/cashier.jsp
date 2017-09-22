<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
    <!--BOOTSTRAP-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <%--link ke css local--%>
    <link rel="stylesheet" href="../../resources/css/main.css"/>
    <link href="https://fonts.googleapis.com/css?family=Rouge+Script" rel="stylesheet">
</head>
<body onload="startTime()">
<nav class="navbar">
    <div class="container-fluid">
        <div class="navbar-header" style="margin-right: 10px;">
            <div class="panel panel-default panel-navbar">
                <div class="panel-body panel-navbar-body">
                    <h6>DEDY KURNIAWAN SANTOSO</h6>
                </div>
            </div>
        </div>
        <div class="navbar-header" style="margin-right: 10px;">
            <div class="panel panel-default panel-navbar">
                <div class="panel-body panel-navbar-body">
                    <h6 id="txtDate"></h6>
                </div>
            </div>
        </div>
        <div class="navbar-header" style="margin-right: 10px;">
            <div class="panel panel-default panel-navbar">
                <div class="panel-body panel-navbar-body">
                    <h6 id="txtJam"></h6>
                </div>
            </div>
        </div>
        <div class="navbar-header navbar-right">
            <a href="#" class="navbar-brand" id="#btnLogOut">logout</a>
        </div>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="container col-md-4" id="listMenu">
            <ul class="row nav nav-tabs">
                <li class="col-md-6 active"><a data-toggle="tab" href="#makanan">FOOD</a></li>
                <li class="col-md-6"><a data-toggle="tab" href="#minuman">DRINK</a></li>
            </ul>
            <div class="tab-content">
                <div id="makanan" class="tab-pane fade in active">
                    <div class="scroll menu">
                        <c:forEach items="${menuList}" var="menu">
                            <c:if test="${menu.getCategory_menu() == 'food'}">
                                <a class="menuCard" href="#" onclick="addToCart(${menu.getId_menu()}, '${menu.getName_menu()}', ${menu.getPrice_total_menu()})">
                                    <div class="panel panel-default panel-menu">
                                        <div class="panel-body">
                                            <img src="https://pbs.twimg.com/profile_images/3665434024/5693c7ebd1873c1efe0955c676a2c41d.jpeg" class="img-thumbnail" alt="ayam goreng"
                                            width="80" height="80" style="margin-right: 10px"/>
                                            <h3>${menu.getName_menu()}</h3>
                                            <h4>Rp ${menu.getPrice_total_menu()}</h4>
                                        </div>
                                    </div>
                                </a>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
                <div id="minuman" class="tab-pane fade">
                    <div class="scroll menu">
                        <c:forEach items="${menuList}" var="menu">
                            <c:if test="${menu.getCategory_menu() == 'drink'}">
                                <a class="menuCard" href="#" onclick="addToCart(${menu.getId_menu()}, '${menu.getName_menu()}', ${menu.getPrice_total_menu()})">
                                    <div class="panel panel-default panel-menu">
                                        <div class="panel-body">
                                            <img src="https://pbs.twimg.com/profile_images/3665434024/5693c7ebd1873c1efe0955c676a2c41d.jpeg" class="img-thumbnail" alt="ayam goreng"
                                                 width="80" height="80" style="margin-right: 10px"/>
                                            <h3>${menu.getName_menu()}</h3>
                                            <h4>Rp ${menu.getPrice_total_menu()}</h4>
                                        </div>
                                    </div>
                                </a>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <div class="container col-md-8">
            <div class="row">
                <div class="col-md-6">
                    <h3>New Order</h3>
                </div>
                <!--<div class="col-md-6">-->
                <!--<div class="input-group">-->
                <!--<div class="input-group-btn">-->
                <!--<button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>-->
                <!--</div>-->
                <!--<input type="text" class="form-control" placeholder="Search" name="search">-->
                <!--</div>-->
                <!--</div>-->
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="scroll">
                        <table class="table table-striped" id="tableCart">
                            <thead>
                            <tr>
                                <th>Menu</th>
                                <th>Qty</th>
                                <th>Item Price</th>
                                <th>Total Price</th>
                                <th></th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                    <div class="col-md-12 totalBayar">
                        <div>
                            <h3 id="totalBayar" style="text-align:center">Rp 0</h3>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <button id="btnNotes" type="button" class="btn" data-toggle="modal" data-target="#modalNotes">NOTES</button>
                    <button id="btnClear" type="button" class="btn" onclick="window.location.reload()">CLEAR</button>
                </div>
                <div class="col-md-1">
                </div>
                <div class="col-md-3">
                    <%--OPEN NEW TAB RECEIPT--%>
                    <%--<form action="/receipt" method="post">--%>
                        <%--&lt;%&ndash;DISINI KASIH INPUT-INPUT YANG ISINYA ADALAH PARAMETER SEPERTI :&ndash;%&gt;--%>
                        <%--&lt;%&ndash;RESTO NAME, RESTO ADDRESS, CUST NAME, PRICE TOTAL, CASH PAID, CASH CHANGE&ndash;%&gt;--%>
                        <%--<input id="restaurant_name" name="restaurantName" type="hidden">--%>
                        <%--<input id="restaurant_address" name="restaurantAddress" type="hidden">--%>
                        <%--<input id="customer_name" name="customerName" type="hidden">--%>
                        <%--<input id="price_total" name="priceTotal" type="hidden">--%>
                        <%--<input id="cash_paid" name="cashPaid" type="hidden">--%>
                        <%--<input id="cash_change" name="cashChange" type="hidden">--%>
                        <%--<button formtarget="_blank" type="submit" class="btn" id="btnPrint">PRINT RECEIPT</button>--%>
                    <%--</form>--%>
                </div>
                <div class="col-md-1">
                </div>
                <div class="col-md-4">
                    <button id="btnPay" type="submit" class="btn" data-toggle="modal" data-target="#paymentModal">PAY</button>
                </div>
            </div>
        </div>
    </div>

    <div id="modalNotes" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="topModal">Notes</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="notes">Notes: </label>
                        <textarea name="notes" class="form-control" id="notes"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="cancel" data-dismiss="modal">Cancel</button>
                    <button type="button" class="submit" data-dismiss="modal" onclick="setNotes()">OK</button>
                </div>
            </div>
        </div>
    </div>
    <div id="paymentModal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="topModal">Payment</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="custName">Name: </label>
                        <input type="text" class="form-control" id="custName"></input>

                        <label for="cash">Cash: </label>
                        <input type="currency" class="form-control" id="cash"></input>

                        <label for="tableNo">Table No: </label>
                        <input type="text" class="form-control" id="tableNo"></input>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="cancel" data-dismiss="modal">Cancel</button>
                    <input type="hidden" name="customerName" id="inputCustName">
                    <input type="hidden" name="tableNo" id="inputTableNo">
                    <input type="hidden" name="notes" id="inputNotes">
                    <input type="hidden" name="priceTotal" id="inputPriceTotal">
                    <%--<input type="hidden" name="array_id_order" id="array_id_order">--%>
                    <%--<input type="hidden" name="array_qty" id="array_qty">--%>
                    <button type="button" class="submit" data-toggle="modal" data-target="#changeModal" onclick="passToChangeModal()">OK</button>
                </div>
            </div>
        </div>
    </div>
    <div id="changeModal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="topModal">Change</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Kembalian: </label>
                        <h5 id="kembalian">Rp 0</h5>
                    </div>
                </div>
                <div class="modal-footer">
                    <%--<a target="_blank" href="/receipt">PRINT RECEIPT</a>--%>
                    <%--DISINI SUBMIT VALUE CUSTOMER NAME, TABLE NO, PRICE TOTAL, DAN NOTES--%>
                    <%--KE CONTROLLER--%>
                    <button id="btnReceipt" type="submit" class="btn" data-toggle="modal" data-target="#receiptModal" onclick="setReceiptData()">RECEIPT</button>
                    <form action="/add-order" method="post">
                        <%--dummy data restaurant--%>
                        <input type="hidden" name="restaurantName" id="inputRestaurantName" value="${restaurant.getRestaurantName()}">
                        <input type="hidden" name="restaurantAddress" id="inputRestaurantAddress" value="${restaurant.getRestaurantAddress()}">
                        <input type="hidden" name="customerName" id="inputCustNameChange">
                        <input type="hidden" name="tableNo" id="inputTableNoChange">
                        <input type="hidden" name="notes" id="inputNotesChange">
                        <input type="hidden" name="priceTotal" id="inputPriceTotalChange">
                        <input type="hidden" name="cashPaid" id="inputCashPaid">
                        <input type="hidden" name="cashChange" id="inputCashChange">
                        <input type="hidden" name="array_id_order" id="array_id_orderChange">
                        <input type="hidden" name="array_qty" id="array_qtyChange">
                        <button type="submit" class="submit">OK</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div id="receiptModal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 id="restaurantNameReceipt" class="topModal" style="text-align:center">${restaurant.getRestaurantName()}</h4>
                    <h4 id="restaurantAddressReceipt" style="text-align: center">${restaurant.getRestaurantAddress()}</h4>
                    <h5 id="custNameReceipt">Dedy</h5>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <table id="tableReceipt" class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Menu</th>
                                    <th>Qty</th>
                                    <th>Total Price</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <h5 id="priceTotalReceipt">Total :  </h5>
                    <h5 id="CashReceipt">Cash :</h5>
                    <h5 id="ChangeReceipt">Change due: </h5>
                </div>
                <div class="modal-footer">
                    <button type="button" class="submit" data-dismiss="modal">OK</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="../../resources/js/order.js"></script>
</body>
</html>