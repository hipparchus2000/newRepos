using Accessibility;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Diagnostics.Eventing.Reader;
using System.Linq;
using System.Security.Permissions;
using System.Security.Policy;
using System.Text;
using System.Threading.Tasks;

namespace XmlRepo
{
    public class Repo
    {
        readonly static string repoFile = "c:\\temp\\XmlRepo.json";
        readonly static string repoFileList = "c:\\temp\\XmlRepoFile.json";

        public static List<RepoObject> RepoList;
        public static List<RepoFileObject> RepoFileList;
        private static object lockObject = new object();

        public Repo()
        {
            //as RepoList is static, ensure single thread access
            lock (lockObject)
            {
                InitialiseRepoList();
                InitialiseRepoFileList();
            }
        }

        private static void InitialiseRepoList()
        {
            if (File.Exists(repoFile))
            {
                string json = File.ReadAllText(repoFile);
                if (json != null)
                {
                    var repoList = JsonConvert.DeserializeObject<List<RepoObject>>(json);
                    RepoList = repoList ?? new List<RepoObject>();
                }
                else
                {
                    RepoList = new List<RepoObject>();
                }
            }
            else
            {
                RepoList = new List<RepoObject>();
            }

        }

        private static void InitialiseRepoFileList()
        {
            if (File.Exists(repoFileList))
            {
                string json = File.ReadAllText(repoFileList);
                if (json != null)
                {
                    var repoFileList = JsonConvert.DeserializeObject<List<RepoFileObject>>(json);
                    RepoFileList = repoFileList ?? new List<RepoFileObject>();
                }
                else
                {
                    RepoFileList = new List<RepoFileObject>();
                }
            }
            else
            {
                RepoFileList = new List<RepoFileObject>();
            }

        }

        public static int AddTopLevel(RepoFile rf)
        {
            if (rf == null) throw new ArgumentNullException();

            //as RepoList is static, ensure single thread access
            lock (lockObject)
            {
                if (RepoList == null)
                {
                    InitialiseRepoList();
                }
                if (RepoFileList == null)
                {
                    InitialiseRepoFileList();
                }
                var matchingFile = RepoFileList
                    .SingleOrDefault(x => x.Name == rf.Name && x.Version == rf.Version && rf.Sha256 == x.Sha256);
                if(matchingFile != null)
                {
                    MessageBox.Show("File already in Repo");
                    return 0;
                }
                RepoFileList.Add(new RepoFileObject { Name = rf.Name, Sha256 = rf.Sha256, Version = rf.Version });
                int count = AddItem(rf.BaseObject);
                SaveRepoList();
                SaveRepoFileList();
                return count;
            }
        }

        private static int AddItem(object Object)
        {
            var dynamic = (dynamic)Object;
            var repoObject = new RepoObject();
            repoObject.Sha256 = dynamic.Sha256;
            var matchingItem = RepoList.SingleOrDefault(x=>x.Sha256 == dynamic.Sha256);
            if (matchingItem != null)
                return 0; //item is already in the repo, no need to duplicate
            string type = (string)dynamic.Type;
            int count = 0;

            switch (type)
            {
                case "Element":
                    var element = (Element)Object;
                    repoObject.Name = element.Name;
                    repoObject.Type = dynamic.Type;
                    foreach(var attr in element.Attributes)
                    {
                        repoObject.ChildAttributeSha256s.Add(attr.Sha256);
                        count += AddItem(attr);
                    }
                    var childList = new List<string>();
                    foreach (var child in element.Children)
                    {
                        childList.Add(child.Sha256);
                        count += AddItem(child);
                    }
                    repoObject.ChildSha256s = childList;
                    RepoList.Add(repoObject);
                    count += 1;
                    break;
                case "CharacterData":
                    var characterData = (CharacterData)Object;
                    repoObject.Type = dynamic.Type;
                    repoObject.CharacterData = characterData.Content;
                    RepoList.Add(repoObject);
                    count += 1; 
                    break;
                case "Attribute":
                    var attribute = (Attribute)Object;
                    repoObject.Type = type;
                    repoObject.Name = attribute.Name;
                    repoObject.Value = attribute.Value; 
                    RepoList.Add(repoObject);
                    count += 1; 
                    break;
                default:
                    MessageBox.Show("Unknown Object Type " + type);
                    break;
            }

            return count;
        }

        private static void SaveRepoList()
        {
            string json = JsonConvert.SerializeObject(RepoList, Formatting.Indented);
            File.WriteAllText(repoFile, json);
        }
        private static void SaveRepoFileList()
        {
            string json = JsonConvert.SerializeObject(RepoFileList, Formatting.Indented);
            File.WriteAllText(repoFileList, json);
        }

        internal static string GetTopLevel(string name, string version)
        {
            lock (lockObject)
            {
                InitialiseRepoList();
                InitialiseRepoFileList();
            }

            var matchingFile = RepoFileList.SingleOrDefault(x => x.Name == name && x.Version == version);
            if(matchingFile == default)
            {
                MessageBox.Show("File Not Found");
                return string.Empty;
            }
            var Object = GetObjectForSha256(matchingFile.Sha256);
            if (typeof(string) == Object.GetType())
                return string.Empty;
            string result = SerializeObjectToXmlText(Object);
            return result;
        }

        private static string SerializeObjectToXmlText(object Object)=> GetXml(Object);

        private static string GetXml(object @object)
        {
            string s = string.Empty;
            dynamic dobj = @object as dynamic;
            if (dobj == null)
            {
                MessageBox.Show("object is null");
                return string.Empty;
            }
            string type = (string)dobj.Type;
            switch(type)
            {
                case "Element":
                    Element element = dobj as Element;
                    s = "<"+element.Name+" ";
                    foreach(var item in element.Attributes)
                    {
                        s += GetXml(item);
                    }
                    s += ">";
                    foreach(var child in element.Children)
                    {
                        s += GetXml(child);
                    }
                    s += "</" + element.Name + ">";

                    return s;
                case "Attribute":
                    Attribute attribute = @object as Attribute;
                    s = attribute.Name + "=\"" + attribute.Value+"\" ";
                    return s;
                case "CharacterData":
                    CharacterData characterData = @object as CharacterData;
                    s = characterData.Content;
                    return s;
            }
            MessageBox.Show("Unknown ObjectType");
            return string.Empty;
        }

        private static object GetObjectForSha256(string sha256)
        {
            RepoObject repoObject = RepoList.SingleOrDefault(x => x.Sha256 == sha256);
            if (repoObject == default)
            {
                MessageBox.Show("Object " + sha256 + " not found in Repo.");
                return string.Empty;
            }

            switch (repoObject.Type)
            {
                case "Element":
                    var attributeList = new List<Attribute>();
                    foreach (var childSha256 in repoObject.ChildAttributeSha256s)
                    {
                        var obj = GetObjectForSha256(childSha256);
                        if (obj.GetType() == typeof(Attribute))
                        {
                            attributeList.Add((Attribute)obj);
                        }
                    }
                    var childObjectList = new List<object>();
                    if (repoObject.ChildSha256s != null)
                    {
                        foreach (var childSha256 in repoObject.ChildSha256s)
                        {
                            childObjectList.Add(GetObjectForSha256(childSha256));
                        }
                    }
                    var element = new Element(repoObject.Name, attributeList, childObjectList);
                    return element;
                case "CharacterData":
                    var characterData = new CharacterData(repoObject.CharacterData);
                    return characterData;
                case "Attribute":
                    var attribute = new Attribute(repoObject.Name, repoObject.Value);
                    return attribute;
                default:
                    MessageBox.Show("Unknown Object Type " + repoObject.Type);
                    return null;
            }
        }
    }

    public class RepoObject
    {
        public RepoObject()
        {
            ChildAttributeSha256s = new List<string>();
            ChildSha256s = new List<string>();
            Name = string.Empty;
            Value = string.Empty;
            Type = string.Empty;
            CharacterData = string.Empty;
            Sha256 = string.Empty;
        }
        public string Name { get; set; }
        public string Value { get; set; }
        public string Type { get; set; }
        public string CharacterData {get;set;}
        public List<string> ChildAttributeSha256s { get; set; }
        public string Sha256 { get; set; }
        public List<string> ChildSha256s { get; set; }
    }

    public class RepoFileObject()
    {
        /// <summary>
        /// File name
        /// </summary>
        public string Name { get; set; }
        public string Version { get; set; }
        public string Sha256 { get; set; }
    }
}
