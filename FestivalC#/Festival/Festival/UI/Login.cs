using Festival.Controller;
using Festival.Model;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Festival.UI
{
    public partial class Login : Form
    {
        //controller
        private ControllerApp appController;

        public Login(ControllerApp appController)
        {
            InitializeComponent();

            this.appController = appController;
            textBoxPassword.PasswordChar = '*';
        }

        /*
         * When the user tries to login 
         */
        private void buttonLogin_Click(object sender, EventArgs e)
        {
            User user = appController.tryLogin(textBoxUsername.Text, textBoxPassword.Text);
            if (user == null)
            {
                labelWarning.Text = "Invalid username/password";
                
            } else
            {
                MainWindow mainWindow = new MainWindow(appController);

                this.Hide();
                mainWindow.Show();
            }
        }

        //if form is closed 
        private void Login_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }
    }
}
