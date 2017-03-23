namespace Festival
{
    partial class MainWindow
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.lblArtist = new System.Windows.Forms.Label();
            this.listBoxArtists = new System.Windows.Forms.ListBox();
            this.dataGridViewShows = new System.Windows.Forms.DataGridView();
            this.dataGridViewSearch = new System.Windows.Forms.DataGridView();
            this.textBoxSearch = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewShows)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewSearch)).BeginInit();
            this.SuspendLayout();
            // 
            // lblArtist
            // 
            this.lblArtist.AutoSize = true;
            this.lblArtist.Location = new System.Drawing.Point(13, 15);
            this.lblArtist.Name = "lblArtist";
            this.lblArtist.Size = new System.Drawing.Size(35, 13);
            this.lblArtist.TabIndex = 1;
            this.lblArtist.Text = "Artists";
            // 
            // listBoxArtists
            // 
            this.listBoxArtists.FormattingEnabled = true;
            this.listBoxArtists.Location = new System.Drawing.Point(12, 40);
            this.listBoxArtists.Name = "listBoxArtists";
            this.listBoxArtists.Size = new System.Drawing.Size(133, 121);
            this.listBoxArtists.TabIndex = 2;
            this.listBoxArtists.SelectedIndexChanged += new System.EventHandler(this.listBoxArtists_SelectedIndexChanged);
            // 
            // dataGridViewShows
            // 
            this.dataGridViewShows.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewShows.Location = new System.Drawing.Point(162, 15);
            this.dataGridViewShows.Name = "dataGridViewShows";
            this.dataGridViewShows.Size = new System.Drawing.Size(462, 150);
            this.dataGridViewShows.TabIndex = 3;
            // 
            // dataGridViewSearch
            // 
            this.dataGridViewSearch.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewSearch.Location = new System.Drawing.Point(12, 270);
            this.dataGridViewSearch.Name = "dataGridViewSearch";
            this.dataGridViewSearch.Size = new System.Drawing.Size(612, 150);
            this.dataGridViewSearch.TabIndex = 4;
            // 
            // textBoxSearch
            // 
            this.textBoxSearch.Location = new System.Drawing.Point(150, 244);
            this.textBoxSearch.Name = "textBoxSearch";
            this.textBoxSearch.Size = new System.Drawing.Size(131, 20);
            this.textBoxSearch.TabIndex = 5;
            this.textBoxSearch.TextChanged += new System.EventHandler(this.textBoxSearch_TextChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(22, 247);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(112, 13);
            this.label1.TabIndex = 6;
            this.label1.Text = "Search shows by date";
            // 
            // MainWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(642, 432);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.textBoxSearch);
            this.Controls.Add(this.dataGridViewSearch);
            this.Controls.Add(this.dataGridViewShows);
            this.Controls.Add(this.listBoxArtists);
            this.Controls.Add(this.lblArtist);
            this.Name = "MainWindow";
            this.Text = "MainWindow";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewShows)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewSearch)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Label lblArtist;
        private System.Windows.Forms.ListBox listBoxArtists;
        private System.Windows.Forms.DataGridView dataGridViewShows;
        private System.Windows.Forms.DataGridView dataGridViewSearch;
        private System.Windows.Forms.TextBox textBoxSearch;
        private System.Windows.Forms.Label label1;
    }
}