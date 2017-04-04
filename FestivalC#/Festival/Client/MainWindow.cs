using Client;
using Festival.Model;
using Festival.Validation.Exceptions;
using System;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Windows.Forms;

namespace Client
{
    public partial class MainWindow : Form
    {

        //app controller
        private ClientController controller;

        //data source
        private BindingList<Artist> artistList;
        private BindingList<Show> showList;
        private BindingList<Show> searchList;

        public MainWindow(ClientController controller)
        {
            InitializeComponent();

            this.controller = controller;
            initialize();
        }

        //initialize graphics components
        private void initialize()
        {
            setArtists();
        }

        /*
         * Populates artist list
         */
        private void setArtists()
        {
            artistList = new BindingList<Artist>(controller.getArtists());
            listBoxArtists.DataSource = artistList;
            listBoxArtists.DisplayMember = "name";
            listBoxArtists.ValueMember = "idArtist";
        }

        /*
         * Populates data grid depending on selected artist
         */
        private void setShows(int idArtist)
        {
            showList = new BindingList<Show>(controller.getShowsForArtist(idArtist));
            dataGridViewShows.DataSource = showList.Select(show => 
                    new {
                        Location = show.Location,
                        Date = show.Date,
                        TicketsAvailable = show.TicketsAvailable,
                        TicketsSold = show.TicketsSold }).ToList();
        }

        /*
         * When artist selection changes
         */
        private void listBoxArtists_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (listBoxArtists.SelectedItem != null)
            {
                Artist selectedArtist = (Artist)listBoxArtists.SelectedItem;
                setShows(selectedArtist.IdArtist);
            }
        }

        /*
         * Sets the searched shows 
         */
        private void setSearchedShows(string date)
        {
            searchList = new BindingList<Show>(controller.getShowsForDate(date));

            dataGridViewSearch.DataSource = searchList.Select(show =>
                    new {
                        Artist = show.Artist.Name,
                        Location = show.Location,
                        Date = show.Date,
                        TicketsAvailable = show.TicketsAvailable,
                        TicketsSold = show.TicketsSold
                    }).ToList();
        }

        /*
         * When the user wants to search for a date 
         */
        private void textBoxSearch_TextChanged(object sender, EventArgs e)
        {
            setSearchedShows(textBoxSearch.Text);
            colorRows();
        }

        /*
         * We want to add a transaction
         */
        private void buttonAdd_Click(object sender, EventArgs e)
        {
            try
            {
                if (dataGridViewSearch.SelectedRows.Count != 1)
                {
                    throw new UIException("Select one row");
                }

                int index = dataGridViewSearch.CurrentRow.Index;
                Show selectedShow = searchList[index];

                string clientName = textBoxClient.Text;
                int numberOfTickets = Int32.Parse(textBoxNrTickets.Text);
                
               // controllerApp.buyTicketsForShow(selectedShow.IdShow, clientName, numberOfTickets);

                //updates UI 
                listBoxArtists_SelectedIndexChanged(this, null);
                textBoxSearch_TextChanged(this, null);
            }
            catch (Exception ex) when (ex is UIException || ex is ValidatorException)
            {
                MessageBox.Show(ex.Message);
            }
            catch (FormatException)
            {
                MessageBox.Show("Number of tickets must be integer");
            }
        }

        //log out 
        private void buttonLogout_Click(object sender, EventArgs e)
        {
            Console.WriteLine("Logout called");

            controller.logout();

            LoginWindow loginForm = new LoginWindow(controller);
            this.Hide();
            loginForm.Show();
        }

        //if form is closed
        private void MainWindow_FormClosed(object sender, FormClosedEventArgs e)
        {
           // Application.Exit();
        }

        /*
         * Colors rows with no tickets in red
         */
        private void colorRows()
        {
            foreach (DataGridViewRow row in dataGridViewSearch.Rows)
            {
                int tickets = Convert.ToInt32(row.Cells[3].Value);
                if (tickets == 0)
                {
                    row.Cells[0].Style.BackColor = Color.Red;
                    row.Cells[1].Style.BackColor = Color.Red;
                    row.Cells[2].Style.BackColor = Color.Red;
                    row.Cells[3].Style.BackColor = Color.Red;
                    row.Cells[4].Style.BackColor = Color.Red;
                }
            }
        }

        private void MainWindow_FormClosing(object sender, FormClosingEventArgs e)
        {
            buttonLogout_Click(this, null);
        }
    }
}
