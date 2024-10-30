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

function myFunction() {
    const input = document.getElementById("myInput");
    const filter = input.value.toUpperCase();
    const table = document.querySelector("table");
    const trs = table.tBodies[0].getElementsByTagName("tr");

    for (let i = 0; i < trs.length; i++) {
        let found = false;
        const tds = trs[i].getElementsByTagName("td");

        for(let j = 0; j < tds.length && !found; j++) {
            if (tds[j].textContent.toUpperCase().indexOf(filter) > -1) {
                found = true;
                break;
            }
        }
        trs[i].style.display = found?"":"none";
    }
}