// �������� ���������� ���� ��� ����� �� ������
document.querySelectorAll('[data-toggle="modal"]').forEach(function (element) {
    element.addEventListener('click', function (event) {
        event.preventDefault();
        var modal = document.getElementById('myModal');
        modal.style.display = 'block';
        var confirmLink = document.getElementById('confirmLink');
        confirmLink.href = this.getAttribute('href');

        document.body.appendChild(modal);
    });
});

// �������� ���������� ���� ��� ������� �� ������ "�������" (?)
document.getElementById('btnCloseModalExit').addEventListener('click', function () {
    var modal = document.getElementById('myModal');
    modal.style.display = 'none';
});

// �������� ���������� ���� ��� ����� �� ��� ���������
window.addEventListener('click', function (event) {
    var modal = document.getElementById('myModal');
    if (event.target == modal) {
        modal.style.display = 'none';
    }
});


const fileInputs = document.querySelectorAll('input[type="file"]');
fileInputs.forEach(function (input) {
    input.setAttribute('multiple', true);
    input.setAttribute('accept', 'image/jpeg, image/png');
});