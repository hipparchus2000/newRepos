namespace XmlDocRepos
{
    partial class XmlDocRepos
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
            this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
            this.filename = new System.Windows.Forms.Label();
            this.chooseFile = new System.Windows.Forms.Button();
            this.process = new System.Windows.Forms.Button();
            this.status = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // openFileDialog1
            // 
            this.openFileDialog1.FileName = "openFileDialog1";
            // 
            // filename
            // 
            this.filename.AutoSize = true;
            this.filename.Location = new System.Drawing.Point(65, 43);
            this.filename.Name = "filename";
            this.filename.Size = new System.Drawing.Size(55, 13);
            this.filename.TabIndex = 0;
            this.filename.Text = "Filename: ";
            // 
            // chooseFile
            // 
            this.chooseFile.Location = new System.Drawing.Point(479, 33);
            this.chooseFile.Name = "chooseFile";
            this.chooseFile.Size = new System.Drawing.Size(75, 23);
            this.chooseFile.TabIndex = 1;
            this.chooseFile.Text = "Choose File";
            this.chooseFile.UseVisualStyleBackColor = true;
            this.chooseFile.Click += new System.EventHandler(this.chooseFile_Click);
            // 
            // process
            // 
            this.process.Location = new System.Drawing.Point(479, 76);
            this.process.Name = "process";
            this.process.Size = new System.Drawing.Size(75, 23);
            this.process.TabIndex = 2;
            this.process.Text = "Process File";
            this.process.UseVisualStyleBackColor = true;
            this.process.Click += new System.EventHandler(this.process_Click);
            // 
            // status
            // 
            this.status.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.status.Location = new System.Drawing.Point(0, 121);
            this.status.Multiline = true;
            this.status.Name = "status";
            this.status.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.status.Size = new System.Drawing.Size(706, 250);
            this.status.TabIndex = 3;
            // 
            // XmlDocRepos
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(706, 371);
            this.Controls.Add(this.status);
            this.Controls.Add(this.process);
            this.Controls.Add(this.chooseFile);
            this.Controls.Add(this.filename);
            this.Name = "XmlDocRepos";
            this.Text = "XmlDocRepos";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.OpenFileDialog openFileDialog1;
        private System.Windows.Forms.Label filename;
        private System.Windows.Forms.Button chooseFile;
        private System.Windows.Forms.Button process;
        private System.Windows.Forms.TextBox status;
    }
}

