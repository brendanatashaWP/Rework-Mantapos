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
</head>
<body>
<nav class="navbar">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">mantapos</a>
        </div>
        <div class="navbar-header navbar-right">
            <a class="navbar-brand" href="#" id="#btnLogOut">logout</a>
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
                    <button id="btnClear" type="button" class="btn">CLEAR</button>
                </div>
                <div class="col-md-1">
                </div>
                <div class="col-md-3">
                    <button type="button" class="btn" id="btnPrint" data-toggle="modal" data-target="#reciptModal">PRINT RECIPT</button>
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

                    <%--<form action="/add-order" method="post">--%>
                        <input type="text" name="customerName" id="inputCustName">
                        <input type="text" name="tableNo" id="inputTableNo">
                        <input type="text" name="notes" id="inputNotes">
                        <input type="text" name="priceTotal" id="inputPriceTotal">
                        <input type="text" name="array_id_order" id="array_id_order">
                        <button type="button" class="submit" data-toggle="modal" data-target="#changeModal" onclick="passToChange()">OK</button>
                    <%--</form>--%>
                    <%--<button type="button" class="submit" data-toggle="modal" data-target="#changeModal">OK</button>--%>
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
                    <%--DISINI SUBMIT VALUE CUSTOMER NAME, TABLE NO, PRICE TOTAL, DAN NOTES--%>
                    <%--KE CONTROLLER--%>
                    <form action="/add-order" method="post">
                        <input type="text" name="customerName" id="inputCustNameChange">
                        <input type="text" name="tableNo" id="inputTableNoChange">
                        <input type="text" name="notes" id="inputNotesChange">
                        <input type="text" name="priceTotal" id="inputPriceTotalChange">
                        <input type="text" name="array_id_order" id="array_id_orderChange">
                        <button type="submit" class="submit">OK</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div id="reciptModal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="topModal" style="text-align:center">RM. Mantap</h4>
                    <h4 style="text-align: center">Jl. Duren 100, Yogyakarta</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>Menu</th>
                                <th>Qty</th>
                                <th>Total Price</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                    <h4>Total :  </h4>
                    <h4>Cash :</h4>
                    <h4>Change due: </h4>
                    <div>
                    </div>
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