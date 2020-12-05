$(document).ready(function () {
    $("#search-slider").slider({
        min: MIN_PRICE,
        max: MAX_PRICE,
        step: 1,
        range: true,
        values: [MIN_PRICE, MAX_PRICE],
        slide: function (event, ui) {
            for (var i = 0; i < ui.values.length; ++i) {
                $("input.sliderValue[data-index=" + i + "]").val(ui.values[i]);
            }
        }
    });

    $("input.sliderValue").change(function () {
        var $this = $(this);
        $("#search-slider").slider("values", $this.data("index"), $this.val());
    });


});

let currentPage = 1;
let txtSearch;
let minPrice;
let maxPrice;
let cateID;
function saveFormVar() {
    txtSearch = $("input[name='txtSearch']").val();
    ;
    minPrice = $("input[name='txtMinPrice']").val();
    maxPrice = $("input[name='txtMaxPrice']").val();
    cateID = $("cakeCateID").val();
}

$(document).on("submit", "#store-search", function (event) {
    currentPage = 1;
    var $form = $(this);

    saveFormVar();

    $.get("search", $form.serialize(), function (response) {
        let result = JSON.parse(response);

        if (result === null || result.length === 0) {
            clearResult();
        } else {
            showResult(result);
        }
    });

    event.preventDefault(); // Important! Prevents submitting the form.
});

$(document).on("click", "#btn-nextPage", function () {
    alert('get');
    $.get("nextPage?curPage=" + (++currentPage)
            + "&txtSearch=" + txtSearch
            + "&txtMinPrice=" + minPrice
            + "&txtMaxPrice=" + maxPrice
            + "&txtCateID=" + cateID,
            function (responseValue) {
                $("#btn-nextPage").remove();

                if (responseValue != null) {
                    let result = JSON.parse(responseValue);

                    if (result != null && result.length > 0) {
                        updateResult(result);
                    }
                }
            });
});

function clearResult() {
    let $searchResult = $("#div-search-result");
    $searchResult.empty();
    $searchResult.prepend('<h4>no result</h4>');
}

function showResult(items) {
    let $searchResult = $("#div-search-result");
    $searchResult.empty();

    let $searchTemp = $("#search-template").clone();
    $searchTemp.removeClass('d-none');
    $searchResult.prepend($searchTemp);

    let $list = $searchTemp.find("#search-result-list").prop('id', 'search-result-list-shown');

    let $itemEle = $("#item-template").clone().removeAttr('id');
    $itemEle.removeClass('d-none');

    let hasMore = false;
    if (items.length > PER_PAGE) {
        items.pop();
        hasMore = true;
    }

    for (let i = 0; i < items.length; i++) {
        console.log('i:' + i);
        let item = items[i];
        let $itemToPrepend = $itemEle.clone();

        $itemToPrepend.find('.div-cake-name').text(item.name);
        $itemToPrepend.find('input[name="txtCakeID"]').val(item.id);
        $itemToPrepend.find('.cake-img').attr('src', item.imagePath);
        $itemToPrepend.find('.btn-add-item').val("$" + item.price);

        $list.append($itemToPrepend);
    }

    if (hasMore) {
        $list.append("<button id='btn-nextPage' class='btn btn-outline-secondary'>more results</button>");
    }
}

function updateResult(items) {
    let $list = $("#search-result-list-shown");

    let $itemEle = $("#item-template").clone().removeAttr('id');
    $itemEle.removeClass('d-none');

    if (items.length > PER_PAGE) {
        items.pop();
        hasMore = true;
    }

    for (let i = 0; i < items.length; i++) {
        console.log('i:' + i);
        let item = items[i];
        let $itemToPrepend = $itemEle.clone();

        $itemToPrepend.find('.div-cake-name').text(item.name);
        $itemToPrepend.find('input[name="txtCakeID"]').val(item.id);
        $itemToPrepend.find('.cake-img').attr('src', item.imagePath);
        $itemToPrepend.find('.btn-add-item').val("$" + item.price);

        $list.append($itemToPrepend);
    }

    if (hasMore) {
        $list.append("<button id='btn-nextPage' class='btn btn-outline-secondary'>more results</button>");
    }
}