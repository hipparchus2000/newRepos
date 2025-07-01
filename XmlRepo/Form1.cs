using System;
using System.Security.Cryptography;
using System.Text;
using System.Xml.Linq;

namespace XmlRepo
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void btnImport_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(txtFilePath.Text))
                return;
            if (File.Exists(txtFilePath.Text) == false)
            {
                MessageBox.Show("File not found");
                return;
            }
            var textData = File.ReadAllText(txtFilePath.Text);
            var version = "1.0";
            int count = XmlHelper.StoreXmlText(txtFilePath.Text, version, textData);
            txtInfoBox.Text += "File " + txtFilePath.Text + " imported. ";
            txtInfoBox.Text += "New Nodes Created " + count + ".\r\n";
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
        }

        private void btnExport_Click(object sender, EventArgs e)
        {
            var version = "1.0";
            var xml = XmlHelper.GetXmlText(txtFilePath.Text, version);
            if (string.IsNullOrEmpty(xml))
                return;
            var newFileName = txtFilePath.Text + ".out.xml";
            xml = FormatXml(xml);
            File.WriteAllText(newFileName, xml);
            txtInfoBox.Text += "File " + newFileName + " exported. ";
        }

        string FormatXml(string xml)
        {
            try
            {
                XDocument doc = XDocument.Parse(xml);
                return doc.ToString();
            }
            catch (Exception)
            {
                // Handle and throw if fatal exception here; don't just ignore them
                return xml;
            }
        }
    }

}
