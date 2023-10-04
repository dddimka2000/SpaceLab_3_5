$(document).ready(function () {
    const links = $(".navbar-nav.iq-main-menu .nav-link");
    const currentPath = window.location.pathname;
    links.removeClass("active");
    links.each(function () {
        const href = $(this).attr("href");
        const currentPathPrefix = getFirstTwoSegments(currentPath); // Получаем первые два сегмента из текущего пути


        if (href === currentPathPrefix) {
            $(this).addClass("active");
        }
    });
});

function getFirstTwoSegments(url) {
    const parts = url.split("/").filter(Boolean); // Разбиваем URL на сегменты и фильтруем пустые элементы
    if (parts.length >= 4) {
        return `/${parts[0]}/${parts[1]}/${parts[2]}`;
    }
    else {
        // Иначе возвращаем URL без изменений
        return url;
    }
}
