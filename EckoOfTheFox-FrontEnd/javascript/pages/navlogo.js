/*same as main ln 17 for sidebar*/
const navLogo = document.getElementById("nav-logo");
const sidebar = document.getElementById("sidebar");
navLogo.addEventListener('click', () => {
        sidebar.classList.toggle("normal-sidebar-show");
})