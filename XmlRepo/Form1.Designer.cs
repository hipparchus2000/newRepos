namespace XmlRepo
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
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
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            btnImport = new Button();
            txtFilePath = new TextBox();
            label1 = new Label();
            txtInfoBox = new TextBox();
            btnExport = new Button();
            SuspendLayout();
            // 
            // btnImport
            // 
            btnImport.Location = new Point(559, 68);
            btnImport.Name = "btnImport";
            btnImport.Size = new Size(112, 34);
            btnImport.TabIndex = 0;
            btnImport.Text = "Import";
            btnImport.UseVisualStyleBackColor = true;
            btnImport.Click += btnImport_Click;
            // 
            // txtFilePath
            // 
            txtFilePath.Location = new Point(111, 71);
            txtFilePath.Name = "txtFilePath";
            txtFilePath.Size = new Size(425, 31);
            txtFilePath.TabIndex = 1;
            txtFilePath.TextChanged += textBox1_TextChanged;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(108, 25);
            label1.Name = "label1";
            label1.Size = new Size(74, 25);
            label1.TabIndex = 2;
            label1.Text = "Filepath";
            // 
            // txtInfoBox
            // 
            txtInfoBox.Location = new Point(12, 141);
            txtInfoBox.Multiline = true;
            txtInfoBox.Name = "txtInfoBox";
            txtInfoBox.ScrollBars = ScrollBars.Both;
            txtInfoBox.Size = new Size(759, 287);
            txtInfoBox.TabIndex = 3;
            // 
            // btnExport
            // 
            btnExport.Location = new Point(677, 68);
            btnExport.Name = "btnExport";
            btnExport.Size = new Size(112, 34);
            btnExport.TabIndex = 4;
            btnExport.Text = "Export";
            btnExport.UseVisualStyleBackColor = true;
            btnExport.Click += btnExport_Click;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(10F, 25F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(800, 450);
            Controls.Add(btnExport);
            Controls.Add(txtInfoBox);
            Controls.Add(label1);
            Controls.Add(txtFilePath);
            Controls.Add(btnImport);
            Name = "Form1";
            Text = "Form1";
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Button btnImport;
        private TextBox txtFilePath;
        private Label label1;
        private TextBox txtInfoBox;
        private Button btnExport;
    }
}
