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
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewShows)).BeginInit();
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
            // MainWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(652, 324);
            this.Controls.Add(this.dataGridViewShows);
            this.Controls.Add(this.listBoxArtists);
            this.Controls.Add(this.lblArtist);
            this.Name = "MainWindow";
            this.Text = "MainWindow";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewShows)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Label lblArtist;
        private System.Windows.Forms.ListBox listBoxArtists;
        private System.Windows.Forms.DataGridView dataGridViewShows;
    }
}