using Festival.Controller;
using Festival.Model;
using Festival.UI;
using Festival.Validation.Exceptions;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Festival
{
    public partial class MainWindow : Form
    {

        //app controller
        private ControllerApp controllerApp;

        //data source
        private BindingList<Artist> artistList;
        private BindingList<Show> showList;
        private BindingList<Show> searchList;

        public MainWindow(ControllerApp controllerApp)
        {
            InitializeComponent();

            this.controllerApp = controllerApp;
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
            artistList = new BindingList<Artist>(controllerApp.getAllArtists());
            listBoxArtists.DataSource = artistList;
            listBoxArtists.DisplayMember = "name";
            listBoxArtists.ValueMember = "idArtist";
        }

        /*
         * Populates data grid depending on selected artist
         */
        private void setShows(int idArtist)
        {
            showList = new BindingList<Show>(controllerApp.getShowsForArtist(idArtist));
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
            searchList = new BindingList<Show>(controllerApp.getShowsForDate(date));

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
                
                controllerApp.buyTicketsForShow(selectedShow.IdShow, clientName, numberOfTickets);

                //updates UI 
                listBoxArtists_SelectedIndexChanged(this, null);
                textBoxSearch_TextChanged(this, null);
            }
            catch (Exception ex) when (ex is UIException || ex is ControllerException || ex is ValidatorException)
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
            Login loginForm = new Login(controllerApp);
            this.Hide();
            loginForm.Show();
        }

        //if form is closed, we close the app
        private void MainWindow_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }
    }
}
