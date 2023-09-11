$(document).ready(function () {
    const links = $(".navbar-nav.iq-main-menu .nav-link");
    const currentPath = window.location.pathname;
    links.removeClass("active");
    links.each(function () {
        const href = $(this).attr("href");
        const currentPathPrefix = getFirstTwoSegments(currentPath);
        console.log(href)
        console.log(currentPathPrefix)

        if (href === currentPathPrefix) {
            $(this).addClass("active");
        }
    });
});

function getFirstTwoSegments(url) {
    const parts = url.split("/").filter(Boolean);
    console.log(parts)
    if (parts.length >= 2) {
        return `/${parts[0]}/${parts[1]}`;
    }
    else {
        // Иначе возвращаем URL без изменений
        return url;
    }
}
