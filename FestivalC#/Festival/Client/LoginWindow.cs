using Services.Validation.Exceptions;
using System;
using System.Windows.Forms;

namespace Client
{
    public partial class LoginWindow : Form
    {
        private ClientController controller;

        public LoginWindow(ClientController controller)
        {
            InitializeComponent();
            this.controller = controller;
            textBoxPassword.PasswordChar = '*';
        }

        private void buttonLogin_Click(object sender, EventArgs e)
        {
            try
            {
                controller.login(textBoxUsername.Text, textBoxPassword.Text);

                MainWindow mainWindow = new MainWindow(controller);

                mainWindow.Show();
                this.Hide();
            } catch (ServiceException ex)
            {
                labelLogin.Text = ex.Message;
            }
        }

        private void LoginWindow_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }
    }
}
