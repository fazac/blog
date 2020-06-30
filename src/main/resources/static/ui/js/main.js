function saveCache(name, value) {
    window.localStorage.setItem(name, value);
}

function getCache(name) {
    return window.localStorage.getItem(name);
}