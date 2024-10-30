function sortTable(columnIndex) {
    var table = document.querySelector("table tbody");
    var rows = Array.from(table.rows);
    var ascending = table.getAttribute("data-sort-order") !== "asc";

    rows.sort(function (rowA, rowB) {
        var cellA = rowA.cells.length > columnIndex ? rowA.cells[columnIndex].innerText : '';
        var cellB = rowB.cells.length > columnIndex ? rowB.cells[columnIndex].innerText : '';

        var numA = parseFloat(cellA) || cellA;
        var numB = parseFloat(cellB) || cellB;

        return ascending ? numA > numB ? 1 : -1 : numA < numB ? 1 : -1;
    });

    table.innerHTML = "";
    rows.forEach(row => table.appendChild(row));
    table.setAttribute("data-sort-order", ascending ? "asc" : "desc");
}