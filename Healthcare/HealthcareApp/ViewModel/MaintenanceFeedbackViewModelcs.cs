/**
 * /*******************************************************************************
 * Copyright 2017 Niklas Klatt
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************
 */
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.IO;
using System.Windows.Input;

using Plugin.Media;
using Plugin.Media.Abstractions;

using Xamarin.Forms;

using HealthcareApp.Model;
using HealthcareApp.Model.Entity;

namespace HealthcareApp.ViewModel
{
    /// <summary>
    ///  ViewModel for MaintenanceFeedbackViewModel
    /// Author: Niklas Klatt
    /// </summary>
    public class MaintenanceFeedbackViewModelcs : INotifyPropertyChanged
    {
        /// <summary>
        /// Current Task, which view is displaying
        /// </summary>
        private Model.Entity.Task task;
        /// <summary>
        /// Change event
        /// </summary>
        public event PropertyChangedEventHandler PropertyChanged;
        /// <summary>
        /// Command for handling PhotoButton
        /// </summary>
        public ICommand PhotoCommand { get; protected set; }
        /// <summary>
        /// Command for handling StoreButton
        /// </summary>
        public ICommand StoreCommamd { get; protected set; }

        /*
        * Data Bindings. For more information see bindings in xaml file.
        */
        private string name;
        public string Name
        {
            get
            {
                return name;
            }
            set
            {
                name = value;
                PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(nameof(Name)));
            }
        }
        private bool buttonActive;
        public bool ButtonActive
        {
            get
            {
                return buttonActive;
            }
            set
            {
                buttonActive = value;
                PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(nameof(ButtonActive)));
            }
        }
        /// <summary>
        /// Items in ListView
        /// </summary>
        public ObservableCollection<Item> Items { get; set; } = new ObservableCollection<Item>();
        /// <summary>
        /// Creates a new MaintenanceFeedbackViewModel
        /// </summary>
        public MaintenanceFeedbackViewModelcs()
        {
            PhotoCommand = new Command(async () => await this.HandlePhotoCommandAsync());
            StoreCommamd = new Command(() => this.HandleStoreCommand());
            task = AppCore.Instance.CurrentTask;

            if (task != null)
            {
                if (task is MaintenanceTask)
                {
                    this.SetListView(((MaintenanceTask)task).Subtasks);
                }
            }
        }
        /// <summary>
        /// Adds a list view Subtasks to ListView
        /// </summary>
        /// <param name="subtaks">List of Subtaks</param>
        private void SetListView(List<AbstractSubTask> subtaks)
        {
            foreach (AbstractSubTask ast in subtaks)
            {
                this.Items.Add(new Item(ast));
            }
        }
        /// <summary>
        /// Definde actions for PhotoCommand
        /// </summary>
        /// <returns></returns>
        private async System.Threading.Tasks.Task HandlePhotoCommandAsync()
        {
            var image = new Image { Aspect = Aspect.AspectFit };
            await CrossMedia.Current.Initialize();
            if (!CrossMedia.Current.IsCameraAvailable || !CrossMedia.Current.IsTakePhotoSupported)
            {
                return;
            }
            var file = await CrossMedia.Current.TakePhotoAsync(new Plugin.Media.Abstractions.StoreCameraMediaOptions
            {
                Directory = "HealthcareApp",
                Name = this.Name,
                PhotoSize = Plugin.Media.Abstractions.PhotoSize.Small
            });

            if (file == null)
                return;
            image.Source = ImageSource.FromStream(() =>
            {
                var stream = file.GetStream();

                return stream;
            });

            Items.Add(new Item(new SubTaskImage(Name, image, this.FileToByteStream(file))));
            Name = "";

        }
        /// <summary>
        /// Definde actions for StoreCommand
        /// </summary>
        private void HandleStoreCommand()
        {

            if (task is MaintenanceTask mt)
            {
                mt.Subtasks.Clear();
                foreach (Item i in Items)
                {
                    if (i.SubTask is SubTaskCheckbox)
                    {
                        mt.consistMaintanceTask = true;
                        mt.Subtasks.Add(new SubTaskCheckbox(i.SubTask.Id, i.Title, i.CheckBox));
                    }
                    if (i.SubTask is SubTaskSlider)
                    {
                        mt.consistMaintanceTask = true;
                        mt.Subtasks.Add(new SubTaskSlider(i.SubTask.Id, i.Title, i.Slider));
                    }
                    if (i.SubTask is SubTaskImage sti)
                    {
                        mt.consistMaintanceTask = true;

                        if (sti.Image != null)
                        {
                            mt.Subtasks.Add(new SubTaskImage(i.Title, i.Image, sti.Image));
                        }
                        else
                        {
                            mt.Subtasks.Add(new SubTaskImage(i.Title, i.Image));
                        }

                    }
                }
                AppCore.Instance.SaveTask(mt);
            }
        }
        /// <summary>
        /// Converts a media file into a byte[]-
        /// </summary>
        /// <param name="file">media file</param>
        /// <returns>bytes of the given media file</returns>
        private byte[] FileToByteStream(MediaFile file)
        {
            var stream = file.GetStream();
            var memoryStream = new MemoryStream();
            stream.CopyTo(memoryStream);
            return memoryStream.ToArray();
        }
        /// <summary>
        /// PropertyChanded implementation
        /// </summary>
        /// <param name="propertyName"> property name</param>
        protected void OnPropertyChanged(string propertyName)
        {
            var handler = PropertyChanged;
            if (handler != null)
                handler(this, new PropertyChangedEventArgs(propertyName));
        }
    }

    /// <summary>
    /// Item for ObservableCollection for ListView
    /// Author: Niklas Klatt
    /// </summary>
    public class Item : INotifyPropertyChanged
    {
        /// <summary>
        /// Subtask behind the ListView entry
        /// </summary>
        public AbstractSubTask SubTask { get; set; }
        public event PropertyChangedEventHandler PropertyChanged;

        /*
        * Data Bindings. For more information see bindings in xaml file.
        */
        private bool checkBoxVisibilty;
        public bool CheckBoxVisibility
        {
            get
            {
                return checkBoxVisibilty;
            }
            set
            {
                checkBoxVisibilty = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(CheckBoxVisibility)));
                }
            }
        }
        private bool sliderVisibility;
        public bool SliderVisibility
        {
            get
            {
                return sliderVisibility;
            }
            set
            {
                sliderVisibility = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(SliderVisibility)));
                }
            }
        }
        private bool imageVisibilty;
        public bool ImageVisibility
        {
            get
            {
                return imageVisibilty;
            }
            set
            {
                imageVisibilty = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(ImageVisibility)));
                }
            }
        }
        private string title;
        public string Title
        {
            get
            {
                return title;
            }
            set
            {
                title = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(Title)));
                }
            }
        }
        private bool checkBox;
        public bool CheckBox
        {
            get
            {
                return checkBox;
            }
            set
            {
                checkBox = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(CheckBox)));
                }
            }
        }
        private int slider;
        public int Slider
        {
            get
            {
                return slider;
            }
            set
            {
                slider = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(Slider)));
                }
            }
        }
        private Image image;
        public Image Image
        {
            get
            {
                return image;
            }
            set
            {
                image = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(Image)));
                }
            }
        }
        /// <summary>
        /// Creates a new Item for ListView
        /// </summary>
        /// <param name="SubTask">Subtask</param>
        public Item(AbstractSubTask SubTask)
        {
            this.SubTask = SubTask;
            this.SetData(SubTask);
        }
        /// <summary>
        /// Set data form Subask to ListView Item
        /// </summary>
        /// <param name="ast">subtask</param>
        private void SetData(AbstractSubTask ast)
        {
            this.Title = ast.Title;
            if (ast is SubTaskCheckbox)
            {
                this.CheckBoxVisibility = true;
                this.SliderVisibility = false;
                this.ImageVisibility = false;
                this.CheckBox = (ast as SubTaskCheckbox).Value;
            }
            if (ast is SubTaskSlider)
            {
                this.CheckBoxVisibility = false;
                this.SliderVisibility = true;
                this.ImageVisibility = false;
                this.Slider = (ast as SubTaskSlider).Value;
            }
            if (ast is SubTaskImage)
            {
                this.CheckBoxVisibility = false;
                this.SliderVisibility = false;
                this.ImageVisibility = true;
                this.Image = (ast as SubTaskImage).Value;
            }
        }
        /// <summary>
        /// PropertyChanded implementation
        /// </summary>
        /// <param name="propertyName"> property name</param>
        protected void OnPropertyChanged(string propertyName)
        {
            var handler = PropertyChanged;
            if (handler != null)
                handler(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
